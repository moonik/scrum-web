import { Injectable } from '@angular/core';
import {HttpClient} from '../shared/http.client.service';

@Injectable()
export class SearchService {

  constructor(private _http: HttpClient) { }

  public searchResults(query) {
    return this._http.get('project/search/' + query)
      .map(res => res.json());
  }

  askForAccess(key: string, username: string) {
    return this._http.post('project/' + key + '/access', username);
  }

}
