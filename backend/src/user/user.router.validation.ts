import { body } from "express-validator";

/**
 * Validates that the body contains a username and that the username is valid.
 */
export const hasValidUsername = body("username")
  .notEmpty()
  .withMessage("No username was provided.")
  .isString()
  .withMessage("The username was invalid.")
  .isLength({ min: 3 })
  .withMessage("The username cannot be shorter than three characters.")
  .isLength({ max: 64 })
  .withMessage("The username cannot be longer than 64 characters.")
  .isAlphanumeric()
  .withMessage("The username can only consist of alphanumeric characters.");

/**
 * Validates that the request body contains a password
 */
export const hasValidPassword = body("password")
  .notEmpty()
  .withMessage("No password was provided.")
  .isString()
  .withMessage("The password was invalid.")
  .isLength({ min: 5 })
  .withMessage("The password cannot be shorter than five characters.");
