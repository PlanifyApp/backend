import jwt from "jsonwebtoken";
import { NextFunction, Request } from "express";

export const jwtAuthMiddleware = (req: any, res: any, next: NextFunction) => {
    const token = req.header("Authorization");

    if (token) {
        const jwtToken = token.substring(7, token.length);

        try {
            const decoded = jwt.verify(jwtToken, process.env.JWT_SECRET || "");
            req.user = decoded;
            next();
        } catch (err) {
            throw new Error("Invalid token");
        }
    } else {
        next();
    }
};
