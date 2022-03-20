import express from "express";
import { createProjectRouter } from "./project/project.factory";
import { createUserRouter } from "./user/user.factory";

export const URL_PREFIX = "/api/v1";

export const app = express();

const userRouter = createUserRouter();
const projectRouter = createProjectRouter();

app.use(express.json());

app.use(URL_PREFIX + "/user", userRouter);
app.use(URL_PREFIX + "/project", projectRouter);
