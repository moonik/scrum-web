import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class IssueConfigurationService {

    constructor(private _http: HttpClient){}

    public createFields(data: any, projectKey: string, issuetype: string) {
        return this._http.post(this.createRequestParams({projectKey: projectKey, issuetype: issuetype}), data)
            .map(res => res.json());
    }

    public getIssueTypes(projectKey: string) {
        return this._http.get('project/' + projectKey + '/issue-types')
            .map(res => res.json());
    }

    public getProjectFields(projectKey: string, issuetype: string) {
        return this._http.get(this.createRequestParams({projectKey: projectKey, issuetype: issuetype}))
            .map(res => res.json());  
    }

    public removeField(id: number, projectKey: string, issuetype: string) {
        return this._http.delete(this.createRequestParams({id: id, projectKey: projectKey, issuetype: issuetype}))
            .map(res => res.json());
    }

    private createRequestParams(data: Object): string {
        let params = Object.entries(data).map(([key, val]) => `${key}=${val}`).join('&');
        return 'project-field?' + params;
    }
}