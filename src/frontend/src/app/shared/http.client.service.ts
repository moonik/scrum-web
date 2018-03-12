import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs, Response, ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {ApplicationConstants} from './applicatins-contants';

@Injectable()
export class HttpClient {

  constructor(private _http: Http, private _constants: ApplicationConstants) {}

  private createRequestOptionsArgs(): RequestOptionsArgs {
    const headers = new Headers();
    headers.append('Authorization', localStorage.getItem('token'));
    headers.append('Content-Type', 'application/json');
    return {
      headers: headers
    };
  }

  get(url) {
    return this._http.get(this._constants.API_URL + url, this.createRequestOptionsArgs());
  }

  post(url: string, body: any): Observable<Response> {
    return this._http.post(this._constants.API_URL + url, JSON.stringify(body), this.createRequestOptionsArgs());
  }

  put(url: string, body: any): Observable<Response> {
    return this._http.put(this._constants.API_URL + url, body, this.createRequestOptionsArgs());
  }

  delete(url: string): Observable<Response> {
    return this._http.delete(this._constants.API_URL + url, this.createRequestOptionsArgs());
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
