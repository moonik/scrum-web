import { Injectable } from '@angular/core';
import {HttpClient} from "../shared/http.client.service";

@Injectable()
export class SearchService {

  constructor(private _http: HttpClient) { }

  searchProjects(phrase){
    return this._http.get('project/search/'+phrase)
      .map(res => res.json());
  }

  searchIssues(phrase){
    return this._http.get('project/issue/search/'+phrase)
      .map(res => res.json());
  }

}
