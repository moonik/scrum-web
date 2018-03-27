import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/finally';
import 'rxjs/add/observable/throw';
import {LoaderService} from '../loader/loader.service';
import * as constants from '../constants/application-constants';

@Injectable()
export class HttpClient {

  private _constants = constants.default;

  constructor(private _http: Http,
              private _loaderService: LoaderService) {
  }

  private createRequestOptionsArgs(): RequestOptionsArgs {
    const headers = new Headers();
    headers.append('Authorization', localStorage.getItem('token'));
    headers.append('Content-Type', 'application/json');
    return {
      headers: headers
    };
  }

  get(url): Observable<any> {
    this.showLoader();
    return this._http.get(this._constants.API_URL + url, this.createRequestOptionsArgs())
      .catch(this.onCatch)
      .do((res: Response) => {
        this.onSuccess(res);
      }, (error: any) => {
        this.onError(error);
      })
      .finally(() => {
        this.onEnd();
      });
  }

  post(url: string, body: any): Observable<any> {
    this.showLoader();
    return this._http.post(this._constants.API_URL + url, JSON.stringify(body), this.createRequestOptionsArgs())
      .catch(this.onCatch)
      .do((res: Response) => {
        this.onSuccess(res);
      }, (error: any) => {
        this.onError(error);
      })
      .finally(() => {
        this.onEnd();
      });
  }

  put(url: string, body: any): Observable<Response> {
    this.showLoader();
    return this._http.put(this._constants.API_URL + url, body, this.createRequestOptionsArgs())
      .catch(this.onCatch)
      .do((res: Response) => {
        this.onSuccess(res);
      }, (error: any) => {
        this.onError(error);
      })
      .finally(() => {
        this.onEnd();
      });
  }

  delete(url: string): Observable<Response> {
    this.showLoader();
    return this._http.delete(this._constants.API_URL + url, this.createRequestOptionsArgs())
      .catch(this.onCatch)
      .do((res: Response) => {
        this.onSuccess(res);
      }, (error: any) => {
        this.onError(error);
      })
      .finally(() => {
        this.onEnd();
      });
  }

  private onCatch(error: any): Observable<any> {
    return Observable.throw(error);
  }

  private onSuccess(res: Response): void {
    console.log(res.toString());
  }

  private onError(res: Response): void {
    console.log(res.toString() + ' - ' + res['_body']);
  }

  private onEnd(): void {
    this.hideLoader();
  }

  private showLoader(): void {
    this._loaderService.show();
  }

  private hideLoader(): void {
    this._loaderService.hide();
  }
}
