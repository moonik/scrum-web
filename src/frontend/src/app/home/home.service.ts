import { Injectable } from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

const URL = 'project/';

@Injectable()
export class HomeService {

  constructor(private _http: HttpClient) {}

  getAllProjects() {
    return this._http.get(URL + 'all').map(res => res.json());
  }
}
