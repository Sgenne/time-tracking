import { randomUUID } from "crypto";
import { Repository } from "../db/repository";
import { Status } from "../service-result";
import ServiceResult from "../service-result/serviceResult.interface";
import { UserService } from "../user/user.service";
import Project from "./project.interface";

export interface ProjectService {
  createProject: (
    title: string,
    owner: string,
    description?: string
  ) => Promise<ServiceResult<Project>>;
  getProjectById: (id: string) => Promise<ServiceResult<Project>>;
}

export default class ProjectServiceProvider implements ProjectService {
  private readonly projectRepository: Repository<Project>;
  private readonly userService: UserService;

  constructor(
    projectRepository: Repository<Project>,
    userService: UserService
  ) {
    this.projectRepository = projectRepository;
    this.userService = userService;
  }

  /**
   * Creates a project.
   *
   * @param title The title of the new project.
   * @param ownerId The id of the owner of the new project.
   * @param description The optional description of the project.
   */
  async createProject(
    title: string,
    ownerId: string,
    description?: string
  ): Promise<ServiceResult<Project>> {
    const { payload: owner, status } = await this.userService.getUser(ownerId);

    if (!owner) {
      return {
        status: status,
        message: "No user with id ownerId was found.",
      };
    }

    const id = randomUUID();

    const project: Project = {
      title: title,
      ownerId: ownerId,
      description: description,
      projectId: id,
    };

    await this.projectRepository.create(project);

    return {
      status: Status.OK,
      payload: project,
    };
  }

  /**
   * Returns a specified project.
   *
   * @param id The id of the project to return.
   */
  async getProjectById(projectId: string): Promise<ServiceResult<Project>> {
    const foundProject: Project | undefined =
      await this.projectRepository.findOne({
        projectId: projectId,
      });

    if (!foundProject) {
      return {
        status: Status.RESOURCE_NOT_FOUND,
      };
    }

    return {
      status: Status.OK,
      payload: foundProject,
    };
  }
}
