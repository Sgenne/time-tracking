import { body } from "express-validator";

/**
 * Validates that the request body contains a valid project title.
 */
export const hasValidProjecTitle = body("title")
  .notEmpty()
  .withMessage("No project title was given.")
  .isString()
  .withMessage("The given title was invalid.");

/**
 * Validates that the request body contains a valid owner-id.
 */
export const hasValidOwnerId = body("ownerId")
  .notEmpty()
  .withMessage("No ownerId was provided.")
  .isString()
  .withMessage("The given ownerId was invalid.");

export const hasOptionalValidProjectDescription = body("description")
  .optional()
  .isString()
  .withMessage("The provided description was invalid.");
