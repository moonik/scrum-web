import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

const URL = 'project-field';

@Injectable()
export class IssueConfigurationService {

    constructor(private http: HttpClient) {}

    createFields(data: any, projectKey: string, issuetype: string) {
        const params = this.http.createRequestParams({projectKey: projectKey, issuetype: issuetype}, URL);
        return this.http.post(params, data).map(res => res.json());
    }

    getIssueTypes(projectKey: string) {
        return this.http.get('project/' + projectKey + '/issue-types')
            .map(res => res.json());
    }

    getProjectFields(projectKey: string, issuetype: string) {
        const params = this.http.createRequestParams({projectKey: projectKey, issuetype: issuetype}, URL);
        return this.http.get(params).map(res => res.json());
    }

    removeField(id: number, projectKey: string, issuetype: string) {
        const params = this.http.createRequestParams({id: id, projectKey: projectKey, issuetype: issuetype}, URL);
        return this.http.delete(params).map(res => res.json());
    }

    createType(data: any, projectKey: string) {
        return this.http.post('project/issue/types/' + projectKey, data).map(res => res.json());
    }

    editType(data: any) {
        return this.http.put('project/issue/edit/types', data).map(res => res.status);
    }

    deleteType(id: number, projectKey: string) {
        return this.http.delete('project/issue/types/' + id + '?projectKey=' + projectKey).map(res => res.status);
    }
}
