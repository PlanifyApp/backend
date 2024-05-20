import express, { Request, Response } from "express";
import { jwtAuth } from "../middlewares/auth";
const { ensureAuth, ensureGuest } = require("../middlewares/auth");

export const userRouter = express.Router();

userRouter.get("/info", (req: any, res: Response) => {
    jwtAuth(req);
    const user = req.user.user;

    return res.status(200).json({
        user: {
            email: user.email,
            image: user.profile_image,
            nickname: user.nickname,
            name: user.name,
        },
    });
});

userRouter.get("/logout", (req: any, res: any) => {
    req.logout();
    res.redirect("/");
});
