import { Injectable } from '@angular/core';
import { HttpClient } from "../shared/http.client.service";

@Injectable()
export class ProjectService {

  constructor(private _http: HttpClient) {

  }

  createProject(object) {
    return this._http.post('project/create', object).map(res => res.json());
  }

}
