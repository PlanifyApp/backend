import express from "express";
import Todo from "../models/schemas/Todo";

export const todoRouter = express.Router();

todoRouter.get("/list", async (req: any, res: any) => {
    const user = req.user?.user;
    const { date } = req.body;

    if (user) {
        const todo = await Todo.find({ user_id: user.id, date: date, is_show: "Y" });
        console.log(todo);

        // return res.status(200).json({
        //     data: {
        //         status: 200,
        //         user: {
        //             id: user.id,
        //             email: user.email,
        //             image: user.profile_image,
        //             nickname: user.nickname,
        //             name: user.name,
        //         },
        //     },
        // });
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
            date: date,
            user_id: user.id,
        });

        const saveData = await newTodo.save();
        console.log(saveData);
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
