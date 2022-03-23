import { Repository } from "../../db/repository";
import { Status } from "../../service-result";
import { UserService } from "../../user/user.service";
import Project from "../project.interface";
import ProjectService from "../project.service";
import { mock, when, instance } from "ts-mockito";
import { createDummyProject, createDummyUser } from "../../setup-tests/mocks";

describe("When the project repository is empty, ", () => {
  const dummyOwner = createDummyUser();
  const dummyProject = createDummyProject();

  const mockProjectRepository: Repository<Project> =
    mock<Repository<Project>>();
  when(mockProjectRepository.create).thenReturn(async (project) => project);
  when(mockProjectRepository.findOne).thenReturn(async () => undefined);
  const projectRepository = instance(mockProjectRepository);

  test("then creating a project fails if the specified owner doesn't exist.", async () => {
    const { title, description, ownerId } = createDummyProject();

    const mockUserService: UserService = mock<UserService>();
    when(mockUserService.getUser).thenReturn(async () => ({
      status: Status.RESOURCE_NOT_FOUND,
    }));

    const userService = instance(mockUserService);

    const projectService = new ProjectService(projectRepository, userService);

    const { payload: createdProject, status } =
      await projectService.createProject(title, ownerId, description);

    expect(createdProject).toBeUndefined();
    expect(status).toBe(Status.RESOURCE_NOT_FOUND);
  });

  test("then creating a project succeeds if the owner exists.", async () => {
    const mockUserService: UserService = mock<UserService>();
    when(mockUserService.getUser(dummyOwner.id)).thenResolve({
      status: Status.OK,
      payload: dummyOwner,
    });

    const userService = instance(mockUserService);

    const projectService = new ProjectService(projectRepository, userService);

    const { payload: createdProject, status } =
      await projectService.createProject(
        dummyProject.title,
        dummyOwner.id,
        dummyProject.description
      );

    if (!createdProject) throw new Error("The project was not created.");

    expect(createdProject.title).toBe(dummyProject.title);
    expect(createdProject.description).toBe(dummyProject.description);
    expect(createdProject.ownerId).toBe(dummyOwner.id);
    expect(status).toBe(Status.OK);
  });

  test("Requesting a project by id returns resource not found", async () => {
    const mockUserService: UserService = mock<UserService>();
    when(mockUserService.getUser(dummyOwner.id)).thenResolve({
      status: Status.OK,
      payload: dummyOwner,
    });

    const userService = instance(mockUserService);

    const projectService = new ProjectService(projectRepository, userService);

    const { status } = await projectService.getProjectById(
      dummyProject.projectId
    );

    expect(status).toBe(Status.RESOURCE_NOT_FOUND);
  });
});

describe("If the project repository is non-empty, ", () => {
  const dummyProject = createDummyProject();

  const mockProjectRepository: Repository<Project> =
    mock<Repository<Project>>();
  when(mockProjectRepository.create).thenReturn(async (project) => project);
  when(mockProjectRepository.findOne).thenReturn(async ({ projectId }) => {
    if (projectId === dummyProject.projectId) return dummyProject;
  });
  const projectRepository = instance(mockProjectRepository);

  test("Requesting an existing project succeeds with 200", async () => {
    const mockUserService = mock<UserService>();
    const userService = instance(mockUserService);

    const projectService = new ProjectService(projectRepository, userService);

    const { status, payload: foundProject } =
      await projectService.getProjectById(dummyProject.projectId);

    if (!foundProject) throw new Error("No project was found.");

    expect(status).toBe(Status.OK);
    expect(foundProject.projectId).toBe(dummyProject.projectId);
  });
});
