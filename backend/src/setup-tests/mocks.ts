import User from "../user/user.interface";

const dummyUsername = "dummyUsername";
const dummyPassword = "dummyPassword";
const dummyId = "dummyId";
const dummyJoinDate = new Date(2022, 2, 20);

export const createDummyUser = (): User => ({
  username: dummyUsername,
  passwordHash: dummyPassword,
  id: dummyId,
  joinDate: dummyJoinDate,
});
