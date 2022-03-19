import { Repository } from "../../db/repository";
import User from "../user.interface";
import UserService from "../user.service";

describe("If no users are registered", () => {
  const mockUserRepository: Repository<User> = {
    findOne: async () => undefined,
    create: async (newUser: User) => newUser,
  };

  const userService = new UserService(mockUserRepository);

  it("then registering with valid credentials succeeds.", async () => {
    const dummyUsername = "dummyUsername";
    const dummyPassword = "dummyPassword";

    const { payload: newUser } = await userService.createUser(
      dummyUsername,
      dummyPassword
    );

    if (!newUser) throw new Error("User was not registered.");

    expect(newUser.username).toBe(dummyUsername);
  });
});

describe("If a user is registered", () => {
  const takenUsername = "takenUsername";
  const existingUser: User = {
    username: takenUsername,
    passwordHash: "skdjhfskdjfhksdf",
    id: "sksjdhf223r4",
    joinDate: new Date(),
  };

  const mockUserRepository: Repository<User> = {
    findOne: async () => existingUser,
    create: async () => undefined,
  };

  const userService = new UserService(mockUserRepository);

  it("then registering with the same username fails", async () => {
    const { payload: newUser } = await userService.createUser(
      takenUsername,
      "abcdefg234234234"
    );

    expect(newUser).toBeUndefined();
  });
});
