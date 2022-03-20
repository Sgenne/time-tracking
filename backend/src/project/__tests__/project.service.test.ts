import { Repository } from "../../db/repository";
import { Status } from "../../service-result";
import { UserService } from "../../user/user.service";
import Project from "../project.interface";
import ProjectService from "../project.service";

const EMPTY_MOCK_PROJECT_REPOSITORY: Repository<Project> = {
  findOne: async () => undefined,
  create: async (project: Project) => project,
};

const USER_NOT_FOUND_MOCK_USER_SERVICE: UserService = {
  createUser: async () => ({ status: Status.OK }),
  getUser: async () => ({ status: Status.RESOURCE_NOT_FOUND }),
};

const MOCK_USER_SERVICE: UserService = {
  createUser: async () => ({ status: Status.OK }),
  getUser: async () => ({
    status: Status.OK,
    payload: {
      id: "id",
      username: "username",
      passwordHash: "passwordHash",
      joinDate: new Date(),
    },
  }),
};

const DUMMY_TITLE = "dummyTitle";
const DUMMY_DESCRIPTION = "dummyDescription";
const DUMMY_OWNER_ID = "dummyUserId";

test("Creating a project fails if the specified owner doesn't exist.", async () => {
  const projectService = new ProjectService(
    EMPTY_MOCK_PROJECT_REPOSITORY,
    USER_NOT_FOUND_MOCK_USER_SERVICE
  );

  const { payload: createdProject, status } =
    await projectService.createProject(
      DUMMY_TITLE,
      DUMMY_OWNER_ID,
      DUMMY_DESCRIPTION
    );

  expect(createdProject).toBeUndefined();
  expect(status).toBe(Status.RESOURCE_NOT_FOUND);
});

test("Creating a project succeeds if the owner exists.", async () => {
  const projectService = new ProjectService(
    EMPTY_MOCK_PROJECT_REPOSITORY,
    MOCK_USER_SERVICE
  );

  const { payload: createdProject, status } =
    await projectService.createProject(
      DUMMY_TITLE,
      DUMMY_OWNER_ID,
      DUMMY_DESCRIPTION
    );

  if (!createdProject) throw new Error("The project was not created.");

  expect(createdProject.title).toBe(DUMMY_TITLE);
  expect(createdProject.description).toBe(DUMMY_DESCRIPTION);
  expect(createdProject.ownerId).toBe(DUMMY_OWNER_ID);
  expect(status).toBe(Status.OK);
});
