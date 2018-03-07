import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class IssueConfigurationService {
    constructor(private _http: HttpClient){}

    public createFields(data, projectKey) {
        return this._http.post('project-field?projectKey='+projectKey+'&issuetype='+'task', data).map(res => res.json());
    }
}