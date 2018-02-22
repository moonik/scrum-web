import { Injectable } from '@angular/core';
import {HttpClient} from "../shared/http.client.service";

@Injectable()
export class SearchService {

  constructor(private _http: HttpClient) { }

  public searchProjects(query){
    return this._http.get('project/search/'+query)
      .map(res => res.json());
  }

  public searchIssues(query){
    return this._http.get('project/issue/search/'+query)
      .map(res => res.json());
  }

}
