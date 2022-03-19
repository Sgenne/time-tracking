import express from "express";
import makeProjectRouter from "./project/project.router";
import { createUserRouter } from "./user/user.factory";

export const app = express();

const userRouter = createUserRouter();

app.use(express.json());

app.use("/api/v1/user", userRouter);
