import { app } from "../../app";
import request from "supertest";
import User from "../user.interface";

describe("If no users are registered", () => {
  it(
    "then registering a user with valid credentials returns 201" +
      "and the new user.",
    async () => {
      const dummyUsername = "dummyUsername";
      const dummyPassword = "dummyPassword";

      const response = await request(app).post("/api/v1/user").send({
        username: dummyUsername,
        password: dummyPassword,
      });

      const createdUser: User = response.body.creted;

      expect(response.status).toBe(201);
      expect(createdUser.username).toBe(dummyUsername);
    }
  );
});
