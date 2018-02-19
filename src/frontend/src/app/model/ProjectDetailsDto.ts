import {ProjectDto} from './projectDto';
import {IssueDto} from './IssueDto';

export class ProjectDetailsDto {
    projectDto: ProjectDto;
    issues: Array<IssueDto>;
}