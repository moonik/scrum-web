import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {IssueDetailsDto} from '../model/IssueDetailsDto';
import {IssueComment} from '../model/IssueComment';

const URL = 'project/issue/';

@Injectable()
export class IssueService {

  constructor(private http: HttpClient) {
  }

  createIssue(projectkey: string, issueDetails: IssueDetailsDto) {
    return this.http.post('project/issue/' + projectkey, issueDetails)
      .map(res => res.json());
  }

  getAssignees(projectKey: string) {
    return this.http.get('project/' + projectKey + '/members')
      .map(res => res.json());
  }

    public getIssueComments(issueId: number) {
      return this.http.get(URL + 'comments/' + issueId)
        .map(res => res.json());
    }

    public addComment(issueId: number, comment: IssueComment) {
        return this.http.post(URL + 'comment/' + issueId, comment)
          .map(res => res.json());
    }

    public deleteComment(commentId: number, issueId: number) {
      return this.http.delete(URL + 'comments/delete/' + commentId + '/' + issueId);
    }

    public editComment(commentId: number, comment: IssueComment) {
      return this.http.post(URL + 'comments/edit/' + commentId, comment);
    }

  getIssueDetails(issueKey: string) {
    return this.http.get(URL + 'details/' + issueKey)
      .map(res => res.json());
  }

  assignToIssue(issueKey: string, username: string) {
    return this.http.post(URL + issueKey + '/assign/' + username, null);
  }

  unAssignFromIssue(username: string, issueKey: string) {
    return this.http.delete(URL + issueKey + '/assign/' + username);
  }
}
