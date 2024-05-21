import express, { Response } from "express";

export const userRouter = express.Router();

userRouter.get("/info", (req: any, res: Response) => {
    const user = req.user?.user;

    if (user) {
        return res.status(200).json({
            data: {
                status: 200,
                user: {
                    id: user.id,
                    email: user.email,
                    image: user.profile_image,
                    nickname: user.nickname,
                    name: user.name,
                },
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

userRouter.get("/logout", (req: any, res: any) => {
    req.logout();
    res.redirect("/");
});
