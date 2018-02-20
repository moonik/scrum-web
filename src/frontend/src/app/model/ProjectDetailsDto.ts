import {ProjectDto} from './projectDto';
import {IssueDto} from './IssueDto';

export class ProjectDetailsDto {
    public projectDto: ProjectDto = new ProjectDto();
    public issues: Array<IssueDto> = [];
}
