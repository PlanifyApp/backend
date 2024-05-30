import express from "express";
import Todo from "../models/schemas/Todo";
import { Types } from "mongoose";

export const todoRouter = express.Router();

todoRouter.get("/list", async (req: any, res: any) => {
    const user = req.user?.user;
    const date = req.query.date;

    if (user) {
        const todo = await Todo.find({
            userId: user._id,
            date: {
                $gte: new Date(date + "T00:00:00Z"),
                $lte: new Date(date + "T23:59:59Z"),
            },
            isShow: "Y",
        });
        const newData: { id: Types.ObjectId; title: string; date: string; isDone: string }[] = [];

        todo.forEach((element) => {
            const newDate = new Date(element.date);
            const year = newDate.getFullYear();
            const month = String(newDate.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더하고, 문자열로 변환합니다.
            const day = String(newDate.getDate()).padStart(2, "0");

            newData.push({
                id: element._id,
                title: element.title,
                date: `${year}-${month}-${day}`,
                isDone: element.isDone,
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

todoRouter.post("/store", async (req: any, res: any) => {
    const user = req.user?.user;
    const { title, date } = req.body;

    if (user) {
        const newTodo = new Todo({
            title: title,
            date: new Date(date),
            userId: user._id,
        });

        const saveData = await newTodo.save();

        if (saveData) {
            return res.status(200).json({
                data: {
                    status: 200,
                    newTodo,
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

todoRouter.put("/check/:id", async (req: any, res: any) => {
    const user = req.user?.user;
    const { id } = req.params;

    if (user) {
        const obId = new Types.ObjectId(id);
        const todoData = await Todo.findOne({ userId: user._id, _id: obId });

        if (todoData) {
            const newIsDone = todoData.isDone === "Y" ? "N" : "Y";
            const updateData = await Todo.findOneAndUpdate(
                { userId: user._id, _id: obId },
                { idDone: newIsDone, modifiedAt: new Date() },
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
