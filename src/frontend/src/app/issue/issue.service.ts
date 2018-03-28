import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {IssueDetailsDto} from '../model/IssueDetailsDto';
import {IssueComment} from "../model/IssueComment";

@Injectable()
export class IssueService {

  private _URL = 'project/issue/';

  constructor(private _http: HttpClient) {}

  createIssue(projectkey: string, issueDetails: IssueDetailsDto) {
    return this._http.post('project/issue/' + projectkey, issueDetails)
      .map(res => res.json());
  }

  getAssignees(projectKey: string) {
    return this._http.get('project/members/' + projectKey)
      .map(res => res.json());
  }

    public getIssueComments(issueId: number) {
      return this._http.get(this._URL+'comments/' + issueId)
        .map(res => res.json());
    }

    public addComment(issueId: number, comment: IssueComment) {
        return this._http.post(this._URL+'comment/' + issueId, comment)
          .map(res => res.json());
    }

    public deleteComment(commentId: number, issueId: number) {
      return this._http.delete(this._URL+'comments/delete/'+commentId+'/'+issueId);
    }

    public editComment(commentId: number, comment: IssueComment){
      return this._http.post(this._URL + 'comments/edit/'+commentId, comment);
    }

  getIssueDetails(issueKey: string) {
    return this._http.get(this._URL + 'details/' + issueKey)
      .map(res => res.json());
  }

  assignToIssue(issueKey: string, username: string) {
    return this._http.post(this._URL + issueKey + '/assign/' + username, null);
  }

  unAssignFromIssue(username: string, issueKey: string) {
    return this._http.delete(this._URL + issueKey + '/assign/' + username);
  }
}
