export default interface Repository<T> {
  findOne: (query: { [key: string]: any }) => Promise<null | T>;
  create: ((t: T) => Promise<T | null>);
}
