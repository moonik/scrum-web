import {ProjectMemberDto} from './projectMemberDto';

export class ProjectDto {
  id: number;
  name: string;
  projectKey: string;
  description: string;
  members: ProjectMemberDto[];
}
