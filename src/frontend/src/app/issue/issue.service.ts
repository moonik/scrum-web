import { Injectable } from '@angular/core';
import { HttpClient } from "../shared/http.client.service";
import { IssueDetailsDto } from '../model/IssueDetailsDto';

@Injectable()
export class IssueService {

    constructor(private _http: HttpClient) {}

    createIssue(projectkey: string, issueDetails: IssueDetailsDto) {
        return this._http.post('project/issue/' + projectkey, issueDetails)
            .map(res => res.json());
    }

    getAssignees(projectKey: string) {
        return this._http.get('project/members/' + projectKey)
            .map(res => res.json());
    }
}