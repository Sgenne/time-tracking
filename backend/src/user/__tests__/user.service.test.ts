import { Repository } from "../../db/repository";
import User from "../user.interface";
import UserService from "../user.service";
import { mock, when, instance } from "ts-mockito";
import { createDummyUser } from "../../setup-tests/mocks";

describe("If no users are registered", () => {
  const mockUserRepository = mock<Repository<User>>();
  when(mockUserRepository.create).thenReturn(async (user) => user);

  const userRepository = instance(mockUserRepository);

  const userService = new UserService(userRepository);

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
  const existingUser = createDummyUser();

  const mockUserRepository = mock<Repository<User>>();
  when(mockUserRepository.findOne).thenReturn(async () => existingUser);

  const userRepository = instance(mockUserRepository);

  const userService = new UserService(userRepository);

  it("then registering with the same username fails", async () => {
    const { payload: newUser } = await userService.createUser(
      existingUser.username,
      existingUser.passwordHash
    );

    expect(newUser).toBeUndefined();
  });
});
