import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class IssueConfigurationService {
    constructor(private http: HttpClient) {}

    createFields(data: any, projectKey: string, issuetype: string) {
        return this.http.post(this.createRequestParams({projectKey: projectKey, issuetype: issuetype}), data)
            .map(res => res.json());
    }

    getIssueTypes(projectKey: string) {
        return this.http.get('project/' + projectKey + '/issue-types')
            .map(res => res.json());
    }

    getProjectFields(projectKey: string, issuetype: string) {
        return this.http.get(this.createRequestParams({projectKey: projectKey, issuetype: issuetype}))
            .map(res => res.json());
    }

    removeField(id: number, projectKey: string, issuetype: string) {
        return this.http.delete(this.createRequestParams({id: id, projectKey: projectKey, issuetype: issuetype}))
            .map(res => res.json());
    }

    createType(data: any, projectKey: string) {
        return this.http.post('project/issue/types/' + projectKey, data).map(res => res.json());
    }

    editType(data: any) {
        return this.http.put('project/issue/edit/types', data).map(res => res.status);
    }

    deleteType(id: number) {
        return this.http.delete('project/issue/types/' + id).map(res => res.status);
    }

    private createRequestParams(data: Object): string {
        const params = Object.entries(data).map(([key, val]) => `${key}=${val}`).join('&');
        return 'project-field?' + params;
    }
}
