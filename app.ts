import dotenv from "dotenv";
dotenv.config();

import express from "express";
import logger from "morgan";
import cookieParser from "cookie-parser";
import path from "path";
import session from "express-session";
import cors from "cors";
import passport from "passport";
import googlePassportConfig from "./passport/googleStrategy";
import naverPassportConfig from "./passport/naverStrategy";
import indexRouter from "./routes/index";
import { connectToMongoDB } from "./db";
import kakaoPassportConfig from "./passport/kakaoStrategy";
import User from "./models/User";

// 세션 타입 확장
declare module "express-session" {
    interface Session {
        passport: { user: string }; // 사용자 정의 타입 설정
        user: number;
    }
}

const app = express();
app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));
app.use(cors());

var sess = {
    secret: "keyboard cat",
    cookie: { secure: false },
};
console.log(app.get("env"));

if (app.get("env") === "production") {
    app.set("trust proxy", 1); // trust first proxy
    sess.cookie.secure = true; // serve secure cookies
}

app.use(session(sess));

// app.use(
//     session({
//         secret: process.env.SESSION_SECRET_KEY || "default-secret-key",
//         resave: false,
//         saveUninitialized: false,
//         cookie: {
//             secure: true,
//         },
//     })
// );

// app.use(passport.initialize());
// app.use(passport.session());

// googlePassportConfig(passport);
// naverPassportConfig(passport);
// kakaoPassportConfig(passport);

// passport.serializeUser(function (user, done) {
//     done(null, user);
// });

// passport.deserializeUser(function (id: string, done) {
//     User.findById(id)
//         .then((user) => done(null, user))
//         .catch((err) => done(err));
// });

app.use("/api", indexRouter);

connectToMongoDB();

module.exports = app;
