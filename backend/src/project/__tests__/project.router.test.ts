import { clearMockDB, closeMockDB, createMockDB } from "../../setup-tests";
import { createDummyProject, createDummyUser } from "../../setup-tests/mocks";
import User from "../../user/user.interface";
import request from "supertest";
import { app, URL_PREFIX } from "../../app";
import userRepository from "../../user/user.repository";
import Project from "../project.interface";
import projectRepository from "../project.repository";

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

test("Requesting an existing project by id succeeds with 200.", async () => {
  const dummyProject = createDummyProject();
  await projectRepository.create(dummyProject);

  const response = await request(app).get(
    URL_PREFIX + "/project/" + dummyProject.projectId
  );

  const foundProject = response.body.project;

  if (!foundProject) throw new Error("No project was found.");

  expect(response.status).toBe(200);
  expect(foundProject.projectId).toBe(dummyProject.projectId);
});

test("Requesting a non-existant project by id fails with 404", async () => {
  const dummyProject = createDummyProject();
  await projectRepository.create(dummyProject);

  const response = await request(app).get(
    URL_PREFIX +
      "/project/" +
      dummyProject.projectId.substring(0, dummyProject.projectId.length - 1)
  );

  const foundProject = response.body.project;

  expect(foundProject).toBeUndefined();
  expect(response.status).toBe(404);
});
