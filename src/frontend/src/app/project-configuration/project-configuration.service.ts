import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {UserDto} from '../model/userDto';
import {ProjectMemberDto} from '../model/projectMemberDto';

@Injectable()
export class ProjectConfigurationService {

  constructor(private _http: HttpClient) {
  }

  public getUsers(members: string[]) {
    return this._http.get('profile/all')
      .map(res => {
        const data = res.json();
        let users: UserDto[] = [];
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

  public addMemberToProject(member: ProjectMemberDto) {
    return this._http.post('project/members/', member);
  }

  public removeMemberFromProject(member: string, projectKey: string) {
    return this._http.delete('project/' + projectKey + '/members/' + member);
  }

  public acceptRequestForAccess(member: ProjectMemberDto) {
    return this._http.post('project/requests', member);
  }

  public declineRequestForAccess(projectKey: string, member: string) {
    return this._http.delete('project/' + projectKey + '/requests/' + member);
  }
}
