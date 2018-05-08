import { Injectable } from '@angular/core';
import {HttpClient} from '../shared/http.client.service';
import {ProjectMemberDto} from '../model/ProjectMemberDto';

@Injectable()
export class SearchService {

  constructor(private http: HttpClient) { }

  searchResults(query) {
    return this.http.get('project/search/' + query)
      .map(res => res.json());
  }

  askForAccess(projectKey: string, member: ProjectMemberDto) {
    return this.http.post('project/' + projectKey + '/access', member);
  }

  findUserInAssignees(assignees: Array<ProjectMemberDto>): boolean {
    return assignees.map(a => a.username).includes(localStorage.getItem('currentUser'));
  }

  findUserInMembers(members: Array<ProjectMemberDto>): boolean {
    return members.map(m => m.username).includes(localStorage.getItem('currentUser'));
  }
}
