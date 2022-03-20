import { NextFunction, Request, Response, Router } from "express";
import RouterCreator from "../router";
import { Status } from "../service-result";
import { handleValidationResult } from "../validation";
import {
  hasOptionalValidProjectDescription,
  hasValidOwnerId,
  hasValidProjecTitle,
} from "./project.router.validation";
import { ProjectService } from "./project.service";

export default class ProjectRouterCreator implements RouterCreator {
  private readonly projectService: ProjectService;

  constructor(projectService: ProjectService) {
    this.projectService = projectService;
  }

  /**
   * Returns a project router.
   */
  createRouter(): Router {
    const router = Router();

    /**
     * Create a project.
     */
    router.post(
      "/",
      hasValidProjecTitle,
      hasValidOwnerId,
      hasOptionalValidProjectDescription,
      handleValidationResult,
      async (req: Request, res: Response, next: NextFunction) => {
        const title = req.body.title;
        const ownerId = req.body.ownerId;
        const description = req.body.description;

        try {
          const { status, payload: createdProject } =
            await this.projectService.createProject(
              title,
              ownerId,
              description
            );

          if (createdProject) {
            return res.status(201).json({ project: createdProject });
          }

          if (status === Status.RESOURCE_NOT_FOUND) {
            return res
              .status(404)
              .json({ message: "The specified owner could not be found." });
          }
          next(
            new Error(
              "The project could not be created due to an unexpeted error."
            )
          );
        } catch (error) {
          return next(error);
        }
      }
    );

    // router.get("/", (req: Request, res: Response) => {
    //   res.status(501).json({ message: "Not implemented." });
    // });

    return router;
  }
}
