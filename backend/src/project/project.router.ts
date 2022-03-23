import { NextFunction, Request, Response, Router } from "express";
import { Status } from "../service-result";
import { handleValidationResult } from "../validation";
import {
  hasOptionalValidProjectDescription,
  hasValidOwnerId,
  hasValidProjectId,
  hasValidProjecTitle,
} from "./project.router.validation";
import { ProjectService } from "./project.service";

const createProjectRouter = (projectService: ProjectService): Router => {
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
          await projectService.createProject(title, ownerId, description);

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

  /**
   * Returns the project with the given id.
   */
  router.get(
    "/:projectId",
    async (req: Request, res: Response, next: NextFunction) => {
      const projectId = req.params.projectId;

      try {
        const { payload: foundProject, status } =
          await projectService.getProjectById(projectId);

        if (foundProject) {
          return res.status(200).json({ project: foundProject });
        }

        if (status === Status.RESOURCE_NOT_FOUND) {
          return res.status(404).json({ message: "No project was found." });
        }

        return next(
          new Error(
            "An unexpected error occured while searching for the project."
          )
        );
      } catch (error) {
        return next(error);
      }
    }
  );

  return router;
};

export default createProjectRouter;
