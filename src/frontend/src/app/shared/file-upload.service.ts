import {Injectable} from "@angular/core";
import {HttpClient} from "./http.client.service";

@Injectable()
export class FileUploadService {

  constructor(private _http: HttpClient) {
  }

  uploadFile(file: File) {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this._http.upload('/api/scrum-web/storage/filename', formData);
  }
}
