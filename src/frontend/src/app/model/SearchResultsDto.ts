import {ProjectDto} from './ProjectDto';
import {IssueDto} from './IssueDto';

export class SearchResultsDto {
  projects: Array<ProjectDto> = [];
  issues: Array<IssueDto> = [];
}
