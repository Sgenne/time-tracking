import { Router } from "express";

export default interface RouterCreator {
  createRouter: () => Router;
}
