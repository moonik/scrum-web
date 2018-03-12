import {ProjectMemberDto} from './projectMemberDto';

export class ProjectDto {
  id: number;
  name: string;
  projectKey: string;
  description: string;
  icon: string;
  image: any;
  members: ProjectMemberDto[];
}
