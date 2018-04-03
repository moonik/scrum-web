import {ProjectDto} from './projectDto';
import {IssueDto} from './IssueDto';

export class SearchResultsDto {
  public project: Array<ProjectDto> = [];
  public issue: Array<IssueDto> = [];
}
