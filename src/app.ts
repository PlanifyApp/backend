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
import kakaoPassportConfig from "./passport/kakaoStrategy";
import indexRouter from "./routes/index";
import { connectToMongoDB } from "./models";
import User from "./models/schemas/User";
import http from "http";
// import debug from "debug";
import MongoStore from "connect-mongo";
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

app.set("port", port);

const server = http.createServer(app);
var debug = require("debug")("backend:server");

// connection error
const onError = (error: any) => {
    if (error.syscall !== "listen") {
        throw error;
    }

    var bind = typeof port === "string" ? "Pipe " + port : "Port " + port;

    // handle specific listen errors with friendly messages
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
};

function onListening() {
    const addr = server.address();
    const bind = typeof addr === "string" ? "pipe " + addr : "port " + addr?.port;
    debug("Listening on " + bind);
}

server.listen(port);
server.on("error", onError);
server.on("listening", onListening);

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
// app.use(express.static(path.join(__dirname, "public")));
app.use(
    session({
        secret: process.env.JWT_SECRET || "",
        resave: true, // don't save session if unmodified
        saveUninitialized: false, // don't create session until something stored
        // store: MongoStore.create({
        //     mongoUrl: process.env.MONGO_URI,
        //     ttl: 2 * 60,
        // }),
        cookie: {
            maxAge: 2 * 60,
            secure: false, // HTTPS를 사용할 경우 true로 설정
            sameSite: "lax", // 또는 'none'으로 설정
        },
    })
);
app.use(csrf());
// app.use(passport.authenticate("session"));
app.use(passport.initialize());
app.use(passport.session());
app.use(cors({ credentials: true }));

// var sess = {
//     secret: "keyboard cat",
//     cookie: { secure: false },
// };
// console.log(app.get("env"));

// if (app.get("env") === "production") {
//     app.set("trust proxy", 1); // trust first proxy
//     sess.cookie.secure = true; // serve secure cookies
// }

// app.use(session(sess));

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

passport.use(googlePassportConfig);

passport.serializeUser(function (id: any, cb) {
    User.findById(id)
        .then((user) => cb(null, user)) // 세션에서 식별자를 기반으로 사용자를 찾음
        .catch((err) => cb(err));
});

passport.deserializeUser(function (user: any, cb) {
    cb(null, user);
});

app.use("/api", indexRouter);
