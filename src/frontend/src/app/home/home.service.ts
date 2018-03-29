import { Injectable } from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

const URL = 'project/';

@Injectable()
export class HomeService {

  constructor(private http: HttpClient) {}

  getAllProjects() {
    return this.http.get(URL + 'all').map(res => res.json());
  }
}
