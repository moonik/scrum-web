import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {UserDto} from '../model/userDto';
import {Observable} from 'rxjs/Observable';
import {ProjectDto} from "../model/projectDto";

@Injectable()
export class ProjectConfigurationService {

  constructor(private _http: HttpClient) {

  }

  getUsers(project: ProjectDto) {
    return this._http.get('/api/scrum-web/profile/all')
      .map(res => {
        const data = res.json();
        console.log('service data: ' + data);
        const users: UserDto[] = [];
        for (const i in data) {
          // if (data[i].username)
          const user: UserDto = new UserDto();
          user.firstname = data[i].firstname;
          user.lastname = data[i].lastname;
          user.username = data[i].username;
          users.push(user);
        }
        return users;
      });
  }



}
