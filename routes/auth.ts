import { NextFunction, Request, Response } from "express";
var passport = require("passport");

var express = require("express");
var router = express.Router();

/* GET home page. */
router.get("/google", passport.authenticate("google", { scope: ["profile", "email"] }));

router.get("/google/callback", passport.authenticate("google", { failureRedirect: "/" }), function (req: Request, res: Response, next: NextFunction) {
    console.log("구글 성공");
});

router.get("/naver", passport.authenticate("naver"));

router.get("/naver/callback", passport.authenticate("naver", { failureRedirect: "/" }), function (req: Request, res: Response, next: NextFunction) {
    console.log("네이버 성공");
});

router.get("/kakao", passport.authenticate("kakao"));

router.get("/kakao/callback", passport.authenticate("kakao", { failureRedirect: "/" }), function (req: Request, res: Response, next: NextFunction) {
    console.log("카카오 성공");
});

module.exports = router;
