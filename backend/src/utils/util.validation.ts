import { NextFunction, Request, Response } from "express";
import { validationResult } from "express-validator";

/**
 * Handles the result of request body validation.
 */
export const handleValidationResult = (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const errors = validationResult(req);

  if (!errors.isEmpty()) {
    const errorMessage = errors.array()[0].msg;

    return res.status(400).json({ message: errorMessage });
  }

  next();
};
