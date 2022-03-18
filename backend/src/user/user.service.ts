import { genSalt, hash } from "bcrypt";
import { randomUUID } from "crypto";
import { Status } from "../service-result";
import ServiceResult from "../service-result/serviceResult.interface";
import User from "./user.interface";
import userRepository from "./user.repository";


export default class UserService {
  async registerUser(
    username: string,
    password: string
  ): Promise<ServiceResult<User>> {
    const usernameOwner: User | null = await userRepository.findOne({
      username: username,
    });

    if (usernameOwner) {
      return {
        status: Status.RESOURCE_OCCUPIED,
      };
    }

    const id: string = randomUUID();
    const passwordHash: string = await generatePasswordHash(password);

    const newUser: User = {
      username: username,
      id: id,
      passwordHash: passwordHash,
      joinDate: new Date(),
    };

    await userRepository.create(newUser);

    return {
      status: Status.OK,
      payload: newUser,
    };
  }
}

const generatePasswordHash = async (password: string): Promise<string> => {
  const salt = await genSalt();
  const passwordHash = await hash(password, salt);
  return passwordHash;
};
