import {ProjectDto} from "./projectDto";
import {IssueDto} from "./IssueDto";

export class SearchResultsDto {
  public projects: Array<ProjectDto> = [];
  public issues: Array<IssueDto> = [];
}
