import express from "express";
import { authRouter } from "./auth";
import { userRouter } from "./user";
import { jwtAuthMiddleware } from "../middlewares/auth";
import { groupRouter } from "./group";
import { todoRouter } from "./todo";
import { scheduleRouter } from "./schedule";

const router = express.Router();

router.use("/auth", authRouter);
router.use("/user", jwtAuthMiddleware, userRouter);
router.use("/group", jwtAuthMiddleware, groupRouter);
router.use("/todo", jwtAuthMiddleware, todoRouter);
router.use("/schedule", jwtAuthMiddleware, scheduleRouter);

export default router;
