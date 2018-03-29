import { Injectable } from '@angular/core';
import { HttpClient } from '../shared/http.client.service';

const URL = 'project/';

@Injectable()
export class ProjectService {

  constructor(private http: HttpClient) {}

  createProject(object) {
    return this.http.post(URL + 'create', object).map(res => res.json());
  }
}
