import express from "express";
import Todo from "../models/schemas/Todo";
import { ObjectId, Types } from "mongoose";

export const todoRouter = express.Router();

todoRouter.get("/list", async (req: any, res: any) => {
    const user = req.user?.user;
    const date = req.query.date;

    if (!user) {
        return res.status(200).json({
            data: {
                status: 401,
                message: "로그인이 필요합니다.",
            },
        });
    }

    if (!date) {
        return res.status(200).json({
            data: {
                status: 400,
                message: "date가 필요합니다.",
            },
        });
    }

    const todo = await Todo.find({
        userId: user._id as ObjectId,
        date: {
            $gte: new Date(date + "T00:00:00Z"),
            $lte: new Date(date + "T23:59:59Z"),
        },
        isShow: "Y",
    });

    const dataList: { id: string; title: string; date: string; isDone: string }[] = [];

    todo.forEach((element) => {
        const newDate = new Date(element.date);
        const year = newDate.getFullYear();
        const month = String(newDate.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더하고, 문자열로 변환합니다.
        const day = String(newDate.getDate()).padStart(2, "0");

        dataList.push({
            id: element._id.toString(),
            title: element.title,
            date: `${year}-${month}-${day}`,
            isDone: element.isDone,
        });
    });

    return res.status(200).json({
        data: {
            status: 200,
            dataList,
        },
    });
});

todoRouter.post("/store", async (req: any, res: any) => {
    const user = req.user?.user;
    const { title, date } = req.body;

    if (!user) {
        return res.status(200).json({
            data: {
                status: 401,
                message: "로그인이 필요합니다.",
            },
        });
    }

    if (!title || !date) {
        return res.status(200).json({
            data: {
                status: 400,
                message: "title과 date가 필요합니다.",
            },
        });
    }

    const newTodo = new Todo({
        title: title,
        date: new Date(date),
        userId: user._id as ObjectId,
    });

    const saveData = await newTodo.save();

    if (saveData) {
        return res.status(200).json({
            data: {
                status: 200,
                saveData,
            },
        });
    }
});

todoRouter.put("/check/:id", async (req: any, res: any) => {
    const user = req.user?.user;
    const { id } = req.params;

    if (!user) {
        return res.status(200).json({
            data: {
                status: 401,
                message: "로그인이 필요합니다.",
            },
        });
    }

    if (!id) {
        return res.status(200).json({
            data: {
                status: 400,
                message: "id가 필요합니다.",
            },
        });
    }

    const obId = id as ObjectId;
    const todoData = await Todo.findOne({ userId: user._id as ObjectId, _id: obId });

    if (!todoData) {
        return res.status(200).json({
            data: {
                status: 400,
                message: "존재하지 않는 id입니다.",
            },
        });
    }

    const newIsDone = todoData.isDone === "Y" ? "N" : "Y";
    const updateData = await Todo.findOneAndUpdate(
        { userId: user._id as ObjectId, _id: obId },
        { isDone: newIsDone, modifiedAt: new Date() },
        { new: true }
    );

    if (updateData) {
        return res.status(200).json({
            data: {
                status: 200,
                updateData,
            },
        });
    }
});

todoRouter.get("/month", async (req: any, res: any) => {
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
        const monthlyTodo = await Todo.aggregate([
            {
                $match: {
                    userId: new Types.ObjectId(user._id),
                    date: {
                        $gte: startDate,
                        $lte: endDate,
                    },
                    isShow: "Y",
                },
            },
            {
                $group: {
                    _id: { $dateToString: { format: "%Y-%m-%d", date: "$date" } },
                    count: { $sum: 1 },
                },
            },
            {
                $sort: { _id: 1 }, // 날짜 순으로 정렬
            },
        ]);

        const dataList: number[] = new Array(lastDay).fill(0);

        monthlyTodo.forEach((element) => {
            const dateString = element._id;
            const parts = dateString.split("-");
            const day = parts[2];

            dataList[day] = element.count;
        });

        return res.status(200).json({
            data: {
                status: 200,
                todo: dataList,
            },
        });
    } catch (error) {
        console.log(error);
    }
});
