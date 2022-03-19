import { app } from "../../app";
import request from "supertest";
import User from "../user.interface";
import { clearMockDB, closeMockDB, createMockDB } from "../../setup-tests";
import userModel from "../user.repository";

beforeAll(async () => await createMockDB());
afterEach(async () => await clearMockDB());
afterAll(async () => await closeMockDB());

describe("If no users are registered", () => {
  it(
    ", then registering a user with valid credentials returns 201" +
      "and the new user.",
    async () => {
      const dummyUsername = "dummyUsername";
      const dummyPassword = "dummyPassword";

      const response = await request(app).post("/api/v1/user").send({
        username: dummyUsername,
        password: dummyPassword,
      });

      const createdUser: User = response.body.created;

      expect(response.status).toBe(201);
      expect(createdUser.username).toBe(dummyUsername);
    }
  );

  describe(", then registering without", () => {
    const validUsername = "validUsername";
    const invalidPassword = "1";
    const validPassword = "validPassword";

    it("a username returns 400.", async () => {
      const response = await request(app).post("/api/v1/user").send({
        password: validPassword,
      });

      expect(response.status).toBe(400);
    });

    it("a valid password returns 400.", async () => {
      const response = await request(app).post("/api/v1/user").send({
        username: validUsername,
        password: invalidPassword,
      });

      expect(response.status).toBe(400);
    });
  });
});

describe("If a user exists", () => {
  const dummyUsername = "dummyUsername";
  const dummyPassword = "dummyPassword";
  const dummyId = "dummyId";

  beforeEach(async () => {
    const existingUser: User = {
      username: dummyUsername,
      passwordHash: dummyPassword,
      id: dummyId,
      joinDate: new Date(),
    };

    await userModel.create(existingUser);
  });

  it(", then registering a new user with the same username returns 403.", async () => {
    const response = await request(app).post("/api/v1/user").send({
      username: dummyUsername,
      password: dummyPassword,
    });

    const createdUser: User | undefined = response.body.created;

    expect(createdUser).toBeUndefined();
    expect(response.status).toBe(403);
  });
});
