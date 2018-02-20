import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserDto} from '../model/userDto';
import {ProjectConfigurationService} from './project-configuration.service';
import {ProjectDto} from '../model/projectDto';
import {StorageService} from '../shared/storage.service';
import {ProjectMemberDto} from '../model/projectMemberDto';

@Component({
  selector: 'app-project-configuration',
  templateUrl: './project-configuration.component.html',
  styleUrls: ['./project-configuration.component.css']
})

export class ProjectConfigurationComponent implements OnInit {

  users: UserDto[] = [];
  project: ProjectDto = new ProjectDto();
  roles: string[] = ['developer', 'tester', 'project manager'];
  selectedRole: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private confService: ProjectConfigurationService,
              private storageService: StorageService) {
  }

  ngOnInit() {
    this.project = this.storageService.getScope();
    this.getAllUsers();
  }

  getAllUsers() {
    const members: string[] = [];
    for (const i of this.project.members) {
      console.log('getAllUsers: ' + i.username);
      members.push(i.username);
    }
    this.confService.getUsers(members).subscribe(
      users => this.users = users
    );
  }

  addUserToProject(user: UserDto) {
    const member: ProjectMemberDto = new ProjectMemberDto();
    member.username = user.username;
    member.projectId = this.project.id;
    member.role = this.selectedRole;

    this.confService.addMemberToProject(member)
      .subscribe(data => {
          this.project.members.push(member);
          this.ngOnInit();
        }
      );
  }

  updateSelected(event: string) {
    this.selectedRole = event;
  }

  removeMemberFromProject(member: ProjectMemberDto) {
    this.confService.removeMemberFromProject(member.username + '/' + member.projectId)
      .subscribe(data => {
          this.project.members.splice(this.project.members.indexOf(member), 1);
          this.ngOnInit();
        }
      );
  }

}