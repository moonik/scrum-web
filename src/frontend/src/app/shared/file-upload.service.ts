import {Injectable} from '@angular/core';
import {HttpClient} from './http.client.service';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class FileUploadService {

  constructor(private _http: HttpClient) {
  }

  uploadFile(file: File) {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this._http.upload('storage/filename', formData);
  }

  loadFile(filename: string): Observable<Blob> {
    return this._http.load('storage/' + filename);
  }

  deleteFile(filename: string) {
    return this._http.delete('storage/' + filename);
  }
}
