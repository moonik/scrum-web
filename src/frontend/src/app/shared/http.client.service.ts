import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs, Response} from '@angular/http';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import * as constants from '../constants/applicatins-contants';

@Injectable()
export class HttpClient {

  private constants = constants.default;

  constructor(private http: Http) {}

  private createRequestOptionsArgs(): RequestOptionsArgs {
    const headers = new Headers();
    headers.append('Authorization', localStorage.getItem('token'));
    headers.append('Content-Type', 'application/json');
    return {
      headers: headers
    };
  }

  get(url) {
    return this.http.get(this.constants.API_URL + url, this.createRequestOptionsArgs());
  }

  post(url: string, body: any): Observable<Response> {
    return this.http.post(this.constants.API_URL + url, JSON.stringify(body), this.createRequestOptionsArgs());
  }

  put(url: string, body: any): Observable<Response> {
    return this.http.put(this.constants.API_URL + url, body, this.createRequestOptionsArgs());
  }

  delete(url: string): Observable<Response> {
    return this.http.delete(this.constants.API_URL + url, this.createRequestOptionsArgs());
  }
}
