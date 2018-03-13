import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class IssueConfigurationService {
    constructor(private _http: HttpClient){}

    public createFields(data, projectKey, issuetype) {
        return this._http.post('project-field?projectKey='+projectKey+'&issuetype='+issuetype, data)
            .map(res => res.json());
    }

    public getIssueTypes(projectKey) {
        return this._http.get('project/' + projectKey + '/issue-types')
            .map(res => res.json());
    }

    public getProjectFields(projectKey: string, issuetype: string) {
        return this._http.get('project-field?projectKey='+projectKey+'&issuetype='+issuetype)
            .map(res => res.json());  
    }
}