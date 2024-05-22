import express from "express";
import Todo from "../models/schemas/Todo";

export const todoRouter = express.Router();

todoRouter.get("/list", async (req: any, res: any) => {
    const user = req.user?.user;

    if (user) {
        const res = await Todo.find({ id: user.id, is_show: true });
        console.log(res);
        // if(res.length > 0)
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

    if (user) {
        console.log(req);
        // const newTodo = new Todo({
        //     title: req.body.title,
        // });

        // const res = await newTodo.save();
        // console.log(res);

        // return res.status(200).json({
        //     data: {
        //         status: 200,
        //         id:
        // })
        // if(res.length > 0)
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
