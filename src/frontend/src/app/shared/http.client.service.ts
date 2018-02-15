import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs, Response, ResponseContentType} from "@angular/http";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";

@Injectable()
export class HttpClient {

  constructor(private _http: Http) {
  }

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

  upload(url: string, formData: any): Observable<Response> {
    const headers = new Headers();
    headers.append('Authorization', localStorage.getItem('token'));
    return this._http.post(url, formData, {headers: headers});
  }

  load(url: string) {
    const headers = new Headers();
    headers.append('Authorization', localStorage.getItem('token'));
    return this._http.get(url, {headers: headers, responseType: ResponseContentType.Blob})
      .map((res: Response) => res.blob());
  }
}
