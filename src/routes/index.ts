import express from "express";
import { authRouter } from "./auth";
import { userRouter } from "./user";
import { jwtAuthMiddleware } from "../middlewares/auth";

const router = express.Router();

router.use("/auth", authRouter);
router.use("/user", jwtAuthMiddleware, userRouter);

export default router;
