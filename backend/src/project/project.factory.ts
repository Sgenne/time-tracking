import { Router } from "express";
import { createUserService } from "../user/user.factory";
import projectRepository from "./project.repository";
import ProjectRouterCreator from "./project.router";
import ProjectServiceProvider, { ProjectService } from "./project.service";

export const createProjectService = (): ProjectService => {
  const userService = createUserService();

  const projectService: ProjectService = new ProjectServiceProvider(
    projectRepository,
    userService
  );

  return projectService;
};

export const createProjectRouter = (): Router => {
  const projectService = createProjectService();
  const projectRouterCreator = new ProjectRouterCreator(projectService);

  return projectRouterCreator.createRouter();
};
