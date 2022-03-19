import { Request, Response, Router } from "express";
import { Status } from "../service-result";
import { handleValidationResult } from "../validation";
import { hasValidPassword, hasValidUsername } from "./user.router.validation";
import UserService from "./user.service";

export const makeUserRouter = (userService: UserService) => {
  const router = Router();

  router.post(
    "/",
    hasValidUsername,
    hasValidPassword,
    handleValidationResult,
    async (req: Request, res: Response) => {
      const username = req.body.username;
      const password = req.body.password;

      try {


        const { payload: registeredUser, status } =
          await userService.registerUser(username, password);


        if (registeredUser) {
          return res.status(201).json({ created: registeredUser });
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
        console.error(error);
        const message =
          error instanceof Error
            ? error.message
            : "The user could not be registered due to an unexpected error.";
        return res.status(500).json({ message: message });
      }
    }
  );
  return router;
};
