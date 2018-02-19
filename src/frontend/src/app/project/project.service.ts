import { Injectable } from '@angular/core';
import { HttpClient } from "../shared/http.client.service";

@Injectable()
export class ProjectService {

  private _URL: string = 'project/';

  constructor(private _http: HttpClient) {

  }

  createProject(object) {
    return this._http.post(this._URL+'create', object).map(res => res.json());
  }

}
