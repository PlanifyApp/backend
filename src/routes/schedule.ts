import express from "express";
import Schedule from "../models/schemas/Schedule";
import { ObjectId, Types } from "mongoose";

export const scheduleRouter = express.Router();

/**
 *
 */
scheduleRouter.post("/store", async (req: any, res: any) => {
    const user = req.user?.user;
    const { title, memo, stDate, enDate, groupId } = req.body;

    if (!user) {
        return res.status(200).json({
            data: {
                status: 401,
                message: "로그인이 필요합니다.",
            },
        });
    }

    if (!title || !memo || !stDate || !enDate || !groupId) {
        return res.status(200).json({
            data: {
                status: 400,
                message: "잘못된 데이터입니다.",
            },
        });
    }

    const newSchedule = new Schedule({
        title: title,
        memo: memo,
        groupId: new Types.ObjectId(groupId),
        startDate: new Date(stDate),
        endDate: new Date(enDate),
        userId: user._id as ObjectId,
    });

    const saveData = await newSchedule.save();

    if (saveData) {
        return res.status(200).json({
            data: {
                status: 200,
                saveData,
            },
        });
    }
});

scheduleRouter.get("/month", async (req: any, res: any) => {
    const user = req.user?.user;
    const { year, month } = req.query;

    if (!user) {
        return res.status(200).json({
            data: {
                status: 401,
                message: "로그인이 필요합니다.",
            },
        });
    }

    if (!year || !month) {
        return res.status(200).json({
            data: {
                status: 400,
                message: "yarn, month가 필요합니다.",
            },
        });
    }

    const startDate = new Date(`${year}-${month.padStart(2, "0")}-01T00:00:00Z`);
    const lastDay = new Date(year, month, 0).getDate(); // 해당 월의 마지막 날 계산
    const endDate = new Date(`${year}-${month.padStart(2, "0")}-${lastDay}T23:59:59Z`);

    try {
        const monthlySchedule = await Schedule.aggregate([
            {
                $match: {
                    userId: new Types.ObjectId(user._id),
                    startDate: {
                        $gte: startDate,
                    },
                    endDate: {
                        $lte: endDate,
                    },
                    isShow: "Y",
                },
            },
            {
                $sort: { startDate: 1 }, // 날짜 순으로 정렬
            },
        ]);

        const dataList: { [key: string]: { id: string; title: string; memo: string; groupId: string; startDate: string; endDate: string }[] } = {};

        monthlySchedule.forEach((element) => {
            const startDateParse = element.startDate.toISOString().replace("T", " ").replace(/\..+/, "");
            const startDateDay = startDateParse.split(/(\s+)/)[0].split("-")[2];
            const endDateParse = element.endDate.toISOString().replace("T", " ").replace(/\..+/, "");

            if (!dataList[startDateDay]) {
                dataList[startDateDay] = [];
            }

            dataList[startDateDay].push({
                id: element._id.toString(),
                title: element.title,
                memo: element.memo,
                groupId: element.groupId.toString(),
                startDate: startDateParse,
                endDate: endDateParse,
            });
        });

        // console.log(dataList);
        return res.status(200).json({
            data: {
                status: 200,
                schedule: dataList,
            },
        });
    } catch (error) {
        console.log(error);
    }
});
