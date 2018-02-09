import {ProjectDto} from './projectDto';
import {IssueDto} from './IssueDto';

export interface ProjectDetailsDto {
    projectDto: ProjectDto;
    issues: Array<IssueDto>;
}