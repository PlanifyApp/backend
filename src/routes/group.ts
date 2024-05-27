import express, { Response } from "express";
import Group from "../models/schemas/Group";

export const groupRouter = express.Router();

groupRouter.get("/list", async (req: any, res: any) => {
    const user = req.user?.user;

    if (user) {
        const group = await Group.find({ user_id: user.id, is_show: "Y" });
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
            user_id: user.id,
        });

        const res = await newGroup.save();
        console.log(res);
        // const group = await Group.find({ user_id: user.id, is_show: true });
        // console.log(group);
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
