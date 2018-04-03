import {UserProfileDto} from './UserProfileDto';

export class IssueComment {
  public id: number;
  public owner: UserProfileDto[];
  public content: string;
  public createdDate: string;
}
