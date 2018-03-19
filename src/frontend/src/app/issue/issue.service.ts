import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {IssueDetailsDto} from '../model/IssueDetailsDto';

@Injectable()
export class IssueService {

  private _URL = 'project/issue/';

  constructor(private _http: HttpClient) {
  }

  createIssue(projectkey: string, issueDetails: IssueDetailsDto) {
    return this._http.post('project/issue/' + projectkey, issueDetails)
      .map(res => res.json());
  }

  getAssignees(projectKey: string) {
    return this._http.get('project/members/' + projectKey)
      .map(res => res.json());
  }

  getIssueDetails(id: number) {
    return this._http.get(this._URL + 'details/' + id)
      .map(res => res.json());
  }

  requestAssign(id: number, username: string) {
    return this._http.post(this._URL + id + '/request/' + username, null);
  }
}
