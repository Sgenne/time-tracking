import { Request, Response, Router } from "express";

const router = Router();

router.use((req: Request, res: Response) =>
  res
    .status(404)
    .json({ message: `The endpoint "${req.url}" is not supported.` })
);

router.use((error: Error, req: Request, res: Response) => {
  console.error(error);
  res.status(500).json({ message: error.message });
});
