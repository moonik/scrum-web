import { Injectable } from '@angular/core';
import { HttpClient } from "../shared/http.client.service";
import { IssueDetailsDto } from '../model/IssueDetailsDto';

@Injectable()
export class IssueService {

    private _URL: string = 'project/issue/';

    constructor(private _http: HttpClient) {}

    getIssueDetails(id: number) {
        return this._http.get(this._URL+'details/'+id)
            .map(res => res.json());
    }
}