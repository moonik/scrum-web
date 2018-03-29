import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class ProjectDetailsService {

    constructor(private httpClient: HttpClient) {}

    getProjectDetails(projectKey: string) {
        return this.httpClient.get('project/details/' + projectKey).map(res => res.json());
    }
}
