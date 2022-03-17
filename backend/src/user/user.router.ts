import { Request, Response, Router } from "express";
import { handleValidationResult } from "../utils/util.validation";
import { hasValidPassword, hasValidUsername } from "./user.router.validation";

export const makeUserRouter = () => {
  const router = Router();

  router.post(
    "/",
    hasValidUsername,
    hasValidPassword,
    handleValidationResult,
    (req: Request, res: Response) => {
      const username = req.body.username;
      const password = req.body.password;

      res.status(501).json({ message: "Not implemented." });
    }
  );
};
