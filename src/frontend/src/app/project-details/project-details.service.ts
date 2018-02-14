import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class ProjectDetailsService {

    constructor(private _httpClient: HttpClient) {}

    public getProjectDetails(projectKey: string) {
        return this._httpClient.get('project/details/' + projectKey)
                    .map(res => res.json());
    }
}