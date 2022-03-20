import { clearMockDB, closeMockDB, createMockDB } from "../../setup-tests";
import { createDummyUser } from "../../setup-tests/mocks";
import User from "../../user/user.interface";
import request from "supertest";
import { app, URL_PREFIX } from "../../app";
import userRepository from "../../user/user.repository";
import Project from "../project.interface";

const DUMMY_TITLE = "DUMMY_TITLE";
const DUMMY_DESCRIPTION = "DUMMY_DESCRIPTION";

beforeAll(async () => await createMockDB());
afterEach(async () => await clearMockDB());
afterAll(async () => await closeMockDB());

test("Creating a valid project returns 201 and the created project.", async () => {
  const dummyUser: User = createDummyUser();

  await userRepository.create(dummyUser);

  const response = await request(app)
    .post(URL_PREFIX + "/project")
    .send({
      title: DUMMY_TITLE,
      description: DUMMY_DESCRIPTION,
      ownerId: dummyUser.id,
    });

  const createdProject: Project = response.body.project;

  expect(response.status).toBe(201);
  expect(createdProject.title).toBe(DUMMY_TITLE);
  expect(createdProject.description).toBe(DUMMY_DESCRIPTION);
  expect(createdProject.ownerId).toBe(dummyUser.id);
});

test("Creating a project with a non-existant owner fails with 404.", async () => {
  const dummyUser: User = createDummyUser();

  const response = await request(app)
    .post(URL_PREFIX + "/project")
    .send({
      title: DUMMY_TITLE,
      description: DUMMY_DESCRIPTION,
      ownerId: dummyUser.id,
    });

  expect(response.status).toBe(404);
  expect(response.body.project).toBeUndefined();
});
