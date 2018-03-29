import { Injectable } from '@angular/core';
import { HttpClient } from '../shared/http.client.service';

const URL = 'project/';

@Injectable()
export class ProjectService {

  constructor(private _http: HttpClient) {}

  public createProject(object) {
    return this._http.post(URL + 'create', object).map(res => res.json());
  }
}
