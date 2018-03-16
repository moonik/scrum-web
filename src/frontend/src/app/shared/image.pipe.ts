import {Pipe, PipeTransform} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';
import {HttpClient} from './http.client.service';

@Pipe({name: 'image'})

export class ImagePipe implements PipeTransform {
  constructor(private _http: HttpClient) {
  }

  transform(url: string) {
    return this._http.load('storage/' + url)
      .map(response => response.blob())
      .switchMap(blob => {
        return Observable.create(observer => {
          const reader = new FileReader();
          reader.readAsDataURL(blob);
          reader.onloadend = function () {
            observer.next(reader.result);
          };
        });
      });
  }
}
