import express, { Response } from "express";
import Group from "../models/schemas/Group";

export const groupRouter = express.Router();

groupRouter.get("/list", async (req: any, res: any) => {
    const user = req.user?.user;

    if (user) {
        const group = await Group.find({ userId: user._id, isShow: "Y" });
        const newData: { title: string; color: string }[] = [];

        group.forEach((element) => {
            newData.push({
                title: element.title,
                color: element.color,
            });
        });

        return res.status(200).json({
            data: {
                status: 200,
                newData,
            },
        });
    } else {
        return res.status(200).json({
            data: {
                status: 401,
                message: "로그인이 필요합니다.",
            },
        });
    }
});

groupRouter.post("/store", async (req: any, res: any) => {
    const user = req.user?.user;
    const { title, color } = req.body;

    if (user) {
        const newGroup = new Group({
            title: title,
            color: color,
            userId: user._id,
        });

        const saveData = await newGroup.save();

        if (saveData) {
            return res.status(200).json({
                data: {
                    status: 200,
                    newGroup,
                },
            });
        }
    } else {
        return res.status(200).json({
            data: {
                status: 401,
                message: "로그인이 필요합니다.",
            },
        });
    }
});
