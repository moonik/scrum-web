import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserDto} from '../model/UserDto';
import {ProjectConfigurationService} from './project-configuration.service';
import {ProjectDto} from '../model/ProjectDto';
import {StorageService} from '../shared/storage.service';
import {ProjectMemberDto} from '../model/ProjectMemberDto';

import * as roles from '../constants/roles';

@Component({
  selector: 'app-project-configuration',
  templateUrl: './project-configuration.component.html',
  styleUrls: ['./project-configuration.component.css']
})

export class ProjectConfigurationComponent implements OnInit {

  users: UserDto[] = [];
  project: ProjectDto = new ProjectDto();
  roles = roles.default;
  rolesTypes = Object.values(this.roles);
  error: string;
  icon: File = null;
  loading = false;
  validIcon = true;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private confService: ProjectConfigurationService,
              private storageService: StorageService) {
  }

  ngOnInit() {
    if (!this.storageService.getScope()) {
      return this.router.navigate(['/home']);
    }
    this.project = this.storageService.getScope();

    if (this.project.icon == null) {
      this.validIcon = false;
    }
    this.getAllUsers();
    this.error = '';
  }

  getAllUsers() {
    const members = [...this.project.members.map(m => m.username),
      ...this.project.requests.map(r => r.username)];
    this.confService.getUsers(members).subscribe(
      users => {
        this.users = users;
      }    );
  }

  addUserToProject(user: string, role: string) {
    const member: ProjectMemberDto = new ProjectMemberDto(user, role);
    this.confService.addMemberToProject(this.project.projectKey, member)
      .subscribe(() => {
          this.project.members.push(member);
          this.ngOnInit();
        }
      );
  }

  removeMemberFromProject(member: ProjectMemberDto) {
    this.confService.removeMemberFromProject(member.username, this.project.projectKey)
      .subscribe(() => {
        this.project.members.splice(this.project.members.indexOf(member), 1);
        this.ngOnInit();
      }, error => {
        if (error.status !== 200) {
          this.error = 'You cannot remove project owner!';
        }
      });
  }

  chooseIcon(files: FileList) {
    this.icon = files.item(0);
    this.loading = true;
    this.validIcon = true;


  }

  acceptRequest(user: string, role: string) {
    const member: ProjectMemberDto = new ProjectMemberDto(user, role);
    this.confService.acceptRequestForAccess(this.project.projectKey, member)
      .subscribe(data => {
          this.project.members.push(member);
          this.project.requests.splice(this.project.requests.indexOf(member), 1);
          this.ngOnInit();
        }
      );
  }

  declineRequest(request: ProjectMemberDto) {
    this.confService.declineRequestForAccess(this.project.projectKey, request.username).subscribe(
      () => {
        this.project.requests.splice(this.project.requests.indexOf(request), 1);
        this.ngOnInit();
      }
    );
  }

  goToIssueConfiguration() {
    this.router.navigate(['/project/' + this.project.projectKey + '/configuration/issues']);
  }
}

