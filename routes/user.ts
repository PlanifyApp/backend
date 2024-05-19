import { Request, Response } from "express";
var express = require("express");
var router = express.Router();

router.get("/info", (req: Request, res: Response) => {
    console.log("call", req.session);

    // return { user: req.session.passport.user };
});

module.exports = router;
