export default interface User {
  id: string;
  username: string;
  passwordHash: string;
  joinDate: Date;
}
