import {UserProfileDto} from './UserProfileDto';

export class IssueComment {
  id: number;
  owner: UserProfileDto[];
  content: string;
  createdDate: string;
  editing: boolean = false;
}

