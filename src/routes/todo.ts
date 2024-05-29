import express from "express";
import Todo from "../models/schemas/Todo";
import { ObjectId } from "mongoose";

export const todoRouter = express.Router();

todoRouter.get("/list", async (req: any, res: any) => {
    const user = req.user?.user;
    const date = req.query.date;

    if (user) {
        const todo = await Todo.find({
            user_id: user.id,
            date: {
                $gte: new Date(date + "T00:00:00Z"),
                $lte: new Date(date + "T23:59:59Z"),
            },
            is_show: "Y",
        });
        const newData: { id: ObjectId; title: string; date: string; is_done: string }[] = [];

        todo.forEach((element) => {
            const newDate = new Date(element.date);
            const year = newDate.getFullYear();
            const month = String(newDate.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더하고, 문자열로 변환합니다.
            const day = String(newDate.getDate()).padStart(2, "0");

            newData.push({
                id: element._id,
                title: element.title,
                date: `${year}-${month}-${day}`,
                is_done: element.is_done,
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
            user_id: user.id,
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
