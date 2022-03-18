import userRepository from "./user.repository";
import { makeUserRouter } from "./user.router";
import UserService from "./user.service";

export const createUserService = () => {



    const userService = new UserService(userRepository)
}

export const createUserRouter = () => {



const userRouter = makeUserRouter(new UserService());

}