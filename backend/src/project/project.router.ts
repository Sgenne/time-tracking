import { Request, Response, Router } from "express";

const makeProjectRouter = () => {
  const router = Router();

  router.get("/", (req: Request, res: Response) => {
    res.status(200).json({ message: "This is a list of projects and!" });
  });

  return router;
};

export default makeProjectRouter;
