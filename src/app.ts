import dotenv from "dotenv";
dotenv.config();

import express from "express";
import logger from "morgan";
import cookieParser from "cookie-parser";
import session from "express-session";
import cors from "cors";
import passport from "passport";
import googlePassportConfig from "./passport/googleStrategy";
import naverPassportConfig from "./passport/naverStrategy";
import kakaoPassportConfig from "./passport/kakaoStrategy";
import indexRouter from "./routes/index";
import { connectToMongoDB } from "./models";
import User from "./models/schemas/User";
import http from "http";
import csrf from "csurf";

// db connection
connectToMongoDB();

// 세션 타입 확장
declare module "express-session" {
    interface Session {
        passport: { user: string }; // 사용자 정의 타입 설정
        user: {
            user: {
                id: string;
                email: string;
                profile_image: string;
                nickname: string;
                name: string;
                type: string;
            };
        };
    }
}

const port = process.env.PORT;
const app = express();
const server = http.createServer(app);
const csrfProtection = csrf({ cookie: true });

app.set("port", port);

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(cors({ credentials: true, origin: "http://localhost:3000" }));

app.use(
    session({
        secret: process.env.JWT_SECRET || "",
        resave: true,
        saveUninitialized: false,
        cookie: {
            maxAge: 2 * 60,
            secure: false, // HTTPS를 사용할 경우 true로 설정
            sameSite: "lax", // 또는 'none'으로 설정
        },
    })
);

//=========== csrf 설정 ===========//
app.use(csrfProtection);
app.use((req, res, next) => {
    console.log(req.csrfToken());
    res.cookie("XSRF-TOKEN", req.csrfToken(), { httpOnly: false, sameSite: "lax" });
    next();
});

//=========== passport 설정 ===========//
app.use(passport.initialize());
app.use(passport.session());

passport.use(googlePassportConfig);
passport.use(naverPassportConfig);
passport.use(kakaoPassportConfig);

passport.serializeUser(function (id: any, cb) {
    User.findById(id)
        .then((user) => cb(null, user)) // 세션에서 식별자를 기반으로 사용자를 찾음
        .catch((err) => cb(err));
});

passport.deserializeUser(function (user: any, cb) {
    cb(null, user);
});

//=========== 라우터 설정 ===========//
app.use("/api", indexRouter);

//=========== 서버 시작 ===========//
server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});

//=========== 서버 에러 핸들링 ===========//
server.on("error", (error: any) => {
    if (error.syscall !== "listen") {
        throw error;
    }
    const bind = typeof port === "string" ? "Pipe " + port : "Port " + port;
    switch (error.code) {
        case "EACCES":
            console.error(bind + " requires elevated privileges");
            process.exit(1);
            break;
        case "EADDRINUSE":
            console.error(bind + " is already in use");
            process.exit(1);
            break;
        default:
            throw error;
    }
});

server.on("listening", () => {
    const addr = server.address();
    const bind = typeof addr === "string" ? "pipe " + addr : "port " + addr?.port;
    console.log("Listening on " + bind);
});
