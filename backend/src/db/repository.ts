import { Model } from "mongoose";

/**
 * A repository allowing database access.
 */
export interface Repository<T> {
  findOne: (query: { [p in keyof T]?: any }) => Promise<T | undefined>;
  create: (t: T) => Promise<T | undefined>;
}

/**
 * Wraps a mongoose model to provide the needed functionality with
 * none of the weird stuff.
 */
export default class RepositoryProvider<T> implements Repository<T> {
  private readonly model: Model<T>;

  constructor(model: Model<T>) {
    this.model = model;
  }

  /**
   * Returns the first found entity that matches the given query.
   *
   * @param query The entity matched agains entities
   */
  async findOne(query: { [p in keyof T]?: any }): Promise<T | undefined> {
    const result: T | null = await this.model.findOne(query);

    if (result) return result;
  }

  /**
   * Inserts the given entity into the repository.
   */
  async create(t: T): Promise<T | undefined> {
    const result: T | null = await this.model.create(t);

    if (result) return result;
  }

  /**
   * Returns all entries matching the given query.
   */
  async find(query: { [p in keyof T]?: any }): Promise<T[]> {
    return this.model.find(query);
  }
}
