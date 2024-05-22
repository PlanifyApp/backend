import express from "express";
import { authRouter } from "./auth";
import { userRouter } from "./user";
import { jwtAuthMiddleware } from "../middlewares/auth";
import { groupRouter } from "./group";
import csrf from "csurf";
const csrfProtection = csrf({ cookie: true });

const router = express.Router();

router.use("/auth", authRouter);
router.use("/user", jwtAuthMiddleware, userRouter);
router.use("/group", jwtAuthMiddleware, groupRouter);

export default router;
