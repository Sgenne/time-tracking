import { Status } from ".";

/**
 * Contains the result of a requested service.
 */
export default interface ServiceResult<T> {
  status: Status;
  payload?: T;
  message?: string;
}
