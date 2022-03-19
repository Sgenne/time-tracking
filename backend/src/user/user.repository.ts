import { Schema, model } from "mongoose";
import Repository from "../db/repository";
import User from "./user.interface";

const userSchema = new Schema<User>({
  username: {
    type: String,
    required: true,
    unique: true,
  },
  passwordHash: {
    type: String,
    required: true,
  },
  id: {
    type: String,
    required: true,
    unique: true,
  },
});

const userModel = model<User>("User", userSchema);

const userRepository = new Repository<User>(userModel);

export default userRepository;
