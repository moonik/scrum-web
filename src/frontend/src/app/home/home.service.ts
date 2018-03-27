import { Injectable } from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class HomeService {

  private _URL = 'project/';

  constructor(private _http: HttpClient) { }

  getAllProjects() {
    return this._http.get(this._URL + 'all').map(res => res.json());
  }

}
