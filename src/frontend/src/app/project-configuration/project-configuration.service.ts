import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {UserDto} from '../model/userDto';
import {ProjectMemberDto} from '../model/projectMemberDto';

@Injectable()
export class ProjectConfigurationService {

  constructor(private http: HttpClient) {
  }

  getUsers(members: string[]) {
    return this.http.get('profile/all')
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
    return this.http.post('project/members/', member);
  }

  removeMemberFromProject(member: string, projectKey: string) {
    return this.http.delete('project/' + projectKey + '/members/' + member);
  }

  acceptRequestForAccess(member: ProjectMemberDto) {
    return this.http.post('project/requests', member);
  }

  declineRequestForAccess(projectKey: string, member: string) {
    return this.http.delete('project/' + projectKey + '/requests/' + member);
  }
}
