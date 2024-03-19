import express from "express";
const router = express.Router();

var groupRouter = require("./group");
router.use("/group", groupRouter);

export default router;
