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
        const token = jwt.sign({ user: req.user }, process.env.JWT_SECRET || "", { expiresIn: "1h" });
        res.cookie("token", token);
        res.redirect("http://localhost:3000");
    }
);

// router.get("/google", (req: Request, res: Response) => {
//     return res.redirect(
//         `https://accounts.google.com/o/oauth2/v2/auth?client_id=${process.env.GOOGLE_CLIENT_ID}&redirect_uri=${process.env.GOOGLE_CALLBACK_URL}&response_type=code&scope=email profile`
//     );
// });

// router.get("/google/callback", async function (req: Request, res: Response) {
//     console.log("query,", req.query);
//     const { code } = req.query;

//     const resp = await axios.post("https://oauth2.googleapis.com/token", {
//         // x-www-form-urlencoded(body)
//         code,
//         client_id: process.env.GOOGLE_CLIENT_ID,
//         client_secret: process.env.GOOGLE_CLIENT_SECRET,
//         redirect_uri: process.env.GOOGLE_CALLBACK_URL,
//         grant_type: "authorization_code",
//     });

//     console.log("resp,", resp);
//     // const resp2 = await axios.get("https://www.googleapis.com/oauth2/v2/userinfo", {
//     //     // Request Header에 Authorization 추가
//     //     headers: {
//     //         Authorization: `Bearer ${resp.data.access_token}`,
//     //     },
//     // });

//     // req.session.user = resp2.data.id;

//     // res.redirect("http://localhost:3000");
// });

// router.get("/naver", passport.authenticate("naver"));

// router.get("/naver/callback", passport.authenticate("naver", { failureRedirect: "/" }), function (req: Request, res: Response, next: NextFunction) {
//     console.log("네이버 성공");
// });

// router.get("/kakao", passport.authenticate("kakao"));

// router.get("/kakao/callback", passport.authenticate("kakao", { failureRedirect: "/" }), function (req: Request, res: Response, next: NextFunction) {
//     console.log("카카오 성공");
// });
