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
    return new Observable<string>((observer) => {
      observer.next('data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==');

      this._http.load('storage/' + url).subscribe(response => {
        const reader = new FileReader();
        reader.readAsDataURL(response.blob());
        reader.onloadend = function() {
          observer.next(reader.result);
        };
      });
      return {unsubscribe() {  }};
    });

  }
}
