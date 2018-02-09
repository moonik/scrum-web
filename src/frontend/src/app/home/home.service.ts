import { Injectable } from '@angular/core';
import {HttpClient} from "../shared/http.client.service";

@Injectable()
export class HomeService {

  constructor(private _http: HttpClient) { }

  getAllOwnProjects() {
    return this._http.get('/api/scrum-web/project/all');
  }

}
