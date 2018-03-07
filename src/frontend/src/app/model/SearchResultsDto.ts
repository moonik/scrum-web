import {ProjectDto} from "./projectDto";
import {IssueDto} from "./IssueDto";

export class SearchResultsDto {
  project: Array<ProjectDto> = [];
  issue: Array<IssueDto> = [];
}
