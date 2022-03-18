import { app } from "./app";
import mongoose from "mongoose";

if (!process.env.DB_URI) throw new Error("DB_URI not set.");

mongoose.connect(process.env.DB_URI).then(() => {
  app.listen(process.env.PORT);
  console.log("Listening on port: " + process.env.PORT);
});
