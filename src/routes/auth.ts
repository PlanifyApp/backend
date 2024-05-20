import axios from "axios";
import express, { NextFunction, Request, Response } from "express";
import passport from "passport";
import jwt from "jsonwebtoken";

export const authRouter = express.Router();

/* GET home page. */
authRouter.get("/google", passport.authenticate("google"));

authRouter.get(
    "/google/callback",
    passport.authenticate("google", {
        failureRedirect: "/login",
    }),
    (req: Request, res: Response) => {
        // const token = jwt.sign({ user: req.user }, process.env.JWT_SECRET || "", { expiresIn: "1h" });
        const token = jwt.sign({ user: req.user }, process.env.JWT_SECRET || "");
        res.cookie("token", token);
        res.redirect("http://localhost:3000");
    }
);

authRouter.get("/naver", passport.authenticate("naver"));

authRouter.get("/naver/callback", passport.authenticate("naver", { failureRedirect: "/" }), (req: Request, res: Response) => {
    const token = jwt.sign({ user: req.user }, process.env.JWT_SECRET || "");
    res.cookie("token", token);
    res.redirect("http://localhost:3000");
});

authRouter.get("/kakao", passport.authenticate("kakao"));

authRouter.get(
    "/kakao/callback",
    passport.authenticate("kakao", { failureRedirect: "/" }),
    function (req: Request, res: Response, next: NextFunction) {
        const token = jwt.sign({ user: req.user }, process.env.JWT_SECRET || "");
        res.cookie("token", token);
        res.redirect("http://localhost:3000");
    }
);
