import { Schema, model } from "mongoose";
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

export default model<User>("User", userSchema);
