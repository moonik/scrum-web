import {ProjectMemberDto} from './projectMemberDto';
import {UserProfileDto} from "./UserProfileDto";

export class ProjectDto {
  id: number;
  name: string;
  projectKey: string;
  description: string;
  owner: UserProfileDto[];
  members: ProjectMemberDto[];
}
