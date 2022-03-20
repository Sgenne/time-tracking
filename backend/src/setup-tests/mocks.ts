import Project from "../project/project.interface";
import User from "../user/user.interface";

const dummyUsername = "dummyUsername";
const dummyPassword = "dummyPassword";
const dummyId = "dummyId";
const dummyJoinDate = new Date(2022, 2, 20);

const dummyTitle = "dummyTitle";
const dummyDescription = "dummyDescription";

export const createDummyUser = (): User => ({
  username: dummyUsername,
  passwordHash: dummyPassword,
  id: dummyId,
  joinDate: dummyJoinDate,
});

export const createDummyProject = (): Project => ({
  title: dummyTitle,
  description: dummyDescription,
  ownerId: dummyId,
  id: dummyId,
});
