import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {Observable} from "rxjs/Observable";
import {ProjectDetailsDto} from '../model/ProjectDetailsDto';

@Injectable()
export class ProjectDetailsService {

    constructor(private _httpClient: HttpClient) {}

    public getProjectDetails(projectKey: string): Observable<ProjectDetailsDto> {
        return this._httpClient.get('project/details/' + projectKey)
                    .map(res => res.json());
    }
}