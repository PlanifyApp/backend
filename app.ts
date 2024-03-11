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

const app = express();

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));
app.use(cors());
app.use(
    session({
        secret: process.env.SESSION_SECRET_KEY || "default-secret-key",
        resave: false,
        saveUninitialized: false,
    })
);

app.use(passport.initialize());
app.use(passport.session());

googlePassportConfig(passport);
naverPassportConfig(passport);
kakaoPassportConfig(passport);

passport.serializeUser(function (user, done) {
    console.log("serialize");
    console.log(user);
    done(null, user);
});

passport.deserializeUser(function (id: string, done) {
    // User.findById(id, function(err, user)){
    //     console.log(user);
    done(null, id);
    // }
});

app.use("/api", indexRouter);

connectToMongoDB();

module.exports = app;
