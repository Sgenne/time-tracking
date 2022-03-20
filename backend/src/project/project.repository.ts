import { Schema, model } from "mongoose";
import Repository from "../db/repository";
import Project from "./project.interface";

const projectSchema = new Schema<Project>({
  title: {
    type: String,
    required: true,
  },
  description: String,
  id: {
    type: String,
    required: true,
    unique: true,
  },
  ownerId: {
    type: String,
    required: true,
  },
});

const projectModel = model<Project>("Project", projectSchema);

const projectRepository = new Repository<Project>(projectModel);

export default projectRepository;
