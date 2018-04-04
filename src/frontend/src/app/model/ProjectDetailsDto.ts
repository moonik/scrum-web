import {ProjectDto} from './ProjectDto';
import {IssueDto} from './IssueDto';

export class ProjectDetailsDto {
    projectDto: ProjectDto = new ProjectDto();
    issues: Array<IssueDto> = [];
}

