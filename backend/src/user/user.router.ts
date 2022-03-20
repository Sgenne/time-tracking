import { NextFunction, Request, Response, Router } from "express";
import { Status } from "../service-result";
import ServiceResult from "../service-result/serviceResult.interface";
import { handleValidationResult } from "../validation";
import User from "./user.interface";
import { hasValidPassword, hasValidUsername } from "./user.router.validation";
import { UserService } from "./user.service";

const createUserRouter = (userService: UserService): Router => {
  const router = Router();

  /**
   * Searches for a user with the given id.
   */
  router.get(
    "/:id",
    async (req: Request, res: Response, next: NextFunction) => {
      const id = req.params.id;

      try {
        const { payload: foundUser, status }: ServiceResult<User> =
          await userService.getUser(id);

        if (foundUser) {
          return res.status(200).json({ user: foundUser });
        }

        if (status === Status.RESOURCE_NOT_FOUND) {
          return res
            .status(404)
            .json({ message: "No user with the given id was found." });
        }
      } catch (error) {
        return next(error);
      }
    }
  );

  /**
   * Creates a user.
   */
  router.post(
    "/",
    hasValidUsername,
    hasValidPassword,
    handleValidationResult,
    async (req: Request, res: Response, next: NextFunction) => {
      const username = req.body.username;
      const password = req.body.password;

      try {
        const { payload: existingUser, status } = await userService.createUser(
          username,
          password
        );

        if (existingUser) {
          return res.status(201).json({ user: existingUser });
        }

        if (status === Status.RESOURCE_OCCUPIED) {
          return res
            .status(403)
            .json({ message: "The chosen username is not available." });
        }

        return res.status(500).json({
          message:
            "The user could not be registered due to an unexpected error.",
        });
      } catch (error) {
        return next(error);
      }
    }
  );

  return router;
};

export default createUserRouter;
