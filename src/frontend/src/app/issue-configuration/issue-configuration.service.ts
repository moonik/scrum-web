import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class IssueConfigurationService {

    constructor(private _http: HttpClient) {}

    public createFields(data: any, projectKey: string, issuetype: string) {
        console.log(data);
        return this._http.post(this.createRequestParams({projectKey: projectKey, issueType: issuetype}), data)
            .map(res => res.json());
    }

    public getIssueTypes(projectKey: string) {
        return this._http.get('project/' + projectKey + '/issue-types')
            .map(res => res.json());
    }

    public getProjectFields(projectKey: string, issueType: string) {
        return this._http.get(this.createRequestParams({projectKey: projectKey, issueType: issueType}))
            .map(res => res.json());
    }

    public removeField(id: number, projectKey: string, issueType: string) {
        return this._http.delete(this.createRequestParams({id: id, projectKey: projectKey, issueType: issueType}))
            .map(res => res.json());
    }

    public createType(data: any, projectKey: string) {
        return this._http.post('project/issue/types/' + projectKey, data)
            .map(res => res.json());
    }

    public editType(data: any) {
        return this._http.put('project/issue/edit/types', data)
            .map(res => res.status);
    }

    public deleteType(id: number) {
        return this._http.delete('project/issue/types/' + id)
            .map(res => res.status);
    }

    private createRequestParams(data: Object): string {
        const params = Object.entries(data).map(([key, val]) => `${key}=${val}`).join('&');
        return 'project-field?' + params;
    }
}
