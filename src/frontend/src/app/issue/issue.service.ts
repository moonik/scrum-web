import { Injectable } from '@angular/core';
import { HttpClient } from "../shared/http.client.service";

@Injectable()
export class IssueService {

    constructor(private _http: HttpClient) {}

    getAssignees(projectKey: string) {
        return this._http.get('project/members/' + projectKey)
            .map(res => res.json());
    }
}