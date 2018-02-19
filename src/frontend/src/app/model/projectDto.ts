import {ProjectMemberDto} from "./projectMemberDto";

export class ProjectDto {
  name: string;
  projectKey: string;
  description: string;
  // members: string[];
  members: ProjectMemberDto[];
}
