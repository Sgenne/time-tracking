import { genSalt, hash } from "bcrypt";
import { randomUUID } from "crypto";
import { Repository } from "../db/repository";
import { Status } from "../service-result";
import ServiceResult from "../service-result/serviceResult.interface";
import User from "./user.interface";

export interface UserService {
  createUser: (
    username: string,
    password: string
  ) => Promise<ServiceResult<User>>;
  getUser: (id: string) => Promise<ServiceResult<User>>;
}

export default class UserServiceProvider implements UserService {
  private readonly userRepository: Repository<User>;

  constructor(userRepository: Repository<User>) {
    this.userRepository = userRepository;
  }

  /**
   * Registers a user with the given credentials.
   *
   * @param username The username of the new user.
   * @param password The password of the new user.
   * @returns A ServiceResult where status is RESOURCE_OCCUPIED if the username is
   * already taken, otherwise OK. If the user was registered, then the payload will
   * be the new user object.
   */
  async createUser(
    username: string,
    password: string
  ): Promise<ServiceResult<User>> {
    const usernameOwner: User | undefined = await this.userRepository.findOne({
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

    await this.userRepository.create(newUser);

    return {
      status: Status.OK,
      payload: newUser,
    };
  }

  /**
   * Returns the user with the given id.
   *
   * @param id - The id of the user to look for.
   * @returns A ServiceResult where status is RESOURCE_NOT_FOUND if no
   * user with the given id exists, otherwise OK.
   */
  async getUser(id: string): Promise<ServiceResult<User>> {
    const user: User | undefined = await this.userRepository.findOne({
      id: id,
    });

    if (!user) {
      return {
        status: Status.RESOURCE_NOT_FOUND,
      };
    }

    return {
      status: Status.OK,
      payload: user,
    };
  }
}

const generatePasswordHash = async (password: string): Promise<string> => {
  const salt = await genSalt();
  const passwordHash = await hash(password, salt);
  return passwordHash;
};
