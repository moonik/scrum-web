import {ProjectMemberDto} from './projectMemberDto';
import {UserProfileDto} from "./UserProfileDto";

export class ProjectDto {
  id: number;
  name: string;
  projectKey: string;
  description: string;
  icon: string;
  image: any;
  owner: UserProfileDto[];
  members: ProjectMemberDto[];
}
