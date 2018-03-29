import {ProjectMemberDto} from './projectMemberDto';
import {UserProfileDto} from './UserProfileDto';

export class ProjectDto {
  public id: number;
  public name: string;
  public projectKey: string;
  public description: string;
  public icon: any;
  public owner: UserProfileDto;
  public members: ProjectMemberDto[];
  public requests: ProjectMemberDto[];
}
