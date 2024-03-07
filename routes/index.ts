import express from 'express';
const router = express.Router();

var authRouter = require('./auth')
router.use('/auth', authRouter);

export default router;
