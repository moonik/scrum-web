import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs, Response} from "@angular/http";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";

@Injectable()
export class HttpClient {

  constructor(private _http: Http) {}

  private createRequestOptionsArgs(): RequestOptionsArgs {
    let headers = new Headers();
    headers.append('Authorization', localStorage.getItem('token'));
    headers.append('Content-Type', 'application/json');
    return { 
      headers: headers
    };
  }

  get(url) {
    return this._http.get(url, this.createRequestOptionsArgs());
  }

  post(url: string, body: any): Observable<Response> {
    return this._http.post(url, JSON.stringify(body), this.createRequestOptionsArgs());
  }

  put(url: string, body: any): Observable<Response> {
    return this._http.put(url, body, this.createRequestOptionsArgs());
  }

  delete(url: string): Observable<Response> {
    return this._http.delete(url, this.createRequestOptionsArgs());
  }
}