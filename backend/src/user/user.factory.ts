import { makeUserRouter } from "./user.router";
import UserService from "./user.service";
import userRepository from "./user.repository";

export const createUserService = () => {
  const userService = new UserService(userRepository);
  return userService;
};

export const createUserRouter = () => {
  const service = createUserService();

  const userRouter = makeUserRouter(service);
  return userRouter;
};
