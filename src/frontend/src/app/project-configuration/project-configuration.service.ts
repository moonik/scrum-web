import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {UserDto} from '../model/userDto';
import {ProjectMemberDto} from '../model/projectMemberDto';

@Injectable()
export class ProjectConfigurationService {

  constructor(private _http: HttpClient) {
  }

  getUsers(members: string[]) {
    return this._http.get('profile/all')
      .map(res => {
        const data = res.json();
        const users: UserDto[] = [];
        for (const i in data) {
          if (!(members.includes(data[i].username))) {
            const user: UserDto = new UserDto();
            user.firstname = data[i].firstname;
            user.lastname = data[i].lastname;
            user.username = data[i].username;
            users.push(user);
          }
        }
        return users;
      });
  }

  addMemberToProject(member: ProjectMemberDto) {
    return this._http.post('project/members/', member);
  }

  removeMemberFromProject(member: string, id: number) {
    return this._http.delete('project/' + id + '/members/' + member);
  }

  changeProjectIcon(key: string, filename: string) {
    console.log("cyccki: " + filename);
    return this._http.post('project/' + key + '/icon/' + filename, null);
  }
}
