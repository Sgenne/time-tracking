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

// Should not export this. Export an object which wraps the wanted functions without all the extra shit.
export default model<User>("User", userSchema);
