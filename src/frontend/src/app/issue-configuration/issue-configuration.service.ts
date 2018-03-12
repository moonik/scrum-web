import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class IssueConfigurationService {
    constructor(private _http: HttpClient){}

    public createFields(data, projectKey, issuetype) {
        return this._http.post('project-field?projectKey='+projectKey+'&issuetype='+issuetype, data)
            .map(res => res.status);
    }

    public getIssueTypes(projectKey) {
        return this._http.get('project/' + projectKey + '/issue-types')
            .map(res => res.json());
    }
}