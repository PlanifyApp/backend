import express from "express";
import session from "express-session";
const router = express.Router();

var authRouter = require("./auth");
var userRouter = require("./user");

router.use("/auth", authRouter);
router.use("/user", userRouter);

export default router;
