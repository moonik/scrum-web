import {Injectable} from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {UserDto} from '../model/UserDto';
import {ProjectMemberDto} from '../model/ProjectMemberDto';

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

  addMemberToProject(projectKey: string, member: ProjectMemberDto) {
    return this.http.post(this.getApiUrl(projectKey) + '/members', member);
  }

  removeMemberFromProject(member: string, projectKey: string) {
    return this.http.delete(this.getApiUrl(projectKey) + '/members/' + member);
  }

  acceptRequestForAccess(projectKey: string, member: ProjectMemberDto) {
    return this.http.post(this.getApiUrl(projectKey) + '/requests/accept', member);
  }

  declineRequestForAccess(projectKey: string, member: string) {
    console.log('decline');
    return this.http.delete(this.getApiUrl(projectKey) + '/requests/decline/' + member);
  }

  private getApiUrl(projectKey: string): string {
    return 'project/' + projectKey;
  }
}
