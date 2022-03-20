import { NextFunction, Request, Response, Router } from "express";
import { Status } from "../service-result";
import { handleValidationResult } from "../validation";
import {
  hasOptionalValidProjectDescription,
  hasValidOwnerId,
  hasValidProjecTitle,
} from "./project.router.validation";
import { ProjectService } from "./project.service";

/**
 * Returns a project router.
 */
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

  return router;
};

export default createProjectRouter;
