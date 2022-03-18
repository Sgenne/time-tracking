import mongoose from "mongoose";
import { MongoMemoryServer } from "mongodb-memory-server";

const db = new MongoMemoryServer();

/**
 * Start a mocked database.
 */
export const createMockDB = async () => {
  await db.start();
  const uri = db.getUri();

  await mongoose.connect(uri);
};

/**
 * Closes the mocked database.
 */
export const closeMockDB = async () => {
  await mongoose.connection.dropDatabase();
  await mongoose.connection.close();
  await db.stop();
};

/**
 * Empties the mocked database.
 */
export const clearMockDB = async () => {
  const collections = mongoose.connection.collections;

  Object.keys(collections).forEach(async (collection) => {
    await collections[collection].deleteMany({});
  });
};
