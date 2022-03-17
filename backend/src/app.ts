import express from "express";
import makeProjectRouter from "./project/project.router";

export const app = express();

const projectRouter = makeProjectRouter();

app.use(express.json());

app.use("/api/v1/project", projectRouter);
