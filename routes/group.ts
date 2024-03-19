import { NextFunction, Request, Response } from "express";

var express = require("express");
var router = express.Router();

/* GET home page. */
router.get("/list", (req: Request, res: Response) => {});

router.post("/modify", (req: Request, res: Response) => {});

router.post("/add", (req: Request, res: Response) => {});

module.exports = router;
