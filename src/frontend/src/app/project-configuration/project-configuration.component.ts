import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserDto} from '../model/userDto';
import {ProjectConfigurationService} from './project-configuration.service';
import {ProjectDto} from '../model/projectDto';
import {StorageService} from '../shared/storage.service';
import {ProjectMemberDto} from '../model/projectMemberDto';

import * as roles from '../constants/roles';

@Component({
  selector: 'app-project-configuration',
  templateUrl: './project-configuration.component.html',
  styleUrls: ['./project-configuration.component.css']
})

export class ProjectConfigurationComponent implements OnInit {

  public users: UserDto[] = [];
  public project: ProjectDto = new ProjectDto();
  public roles = roles.default;
  public rolesTypes = Object.values(this.roles);
  public error: string;
  public icon: File = null;
  public loading = false;
  public validIcon = true;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private confService: ProjectConfigurationService,
              private storageService: StorageService) {
  }

  public ngOnInit() {
    if (!this.storageService.getScope()) {
      this.router.navigate(['/home']);
    }
    this.project = this.storageService.getScope();

    if (this.project.icon == null) {
      this.validIcon = false;
    }
    this.getAllUsers();
    this.error = '';
  }

  public getAllUsers() {
    let members = [...this.project.members.map(m => m.username),
      ...this.project.requests.map(r => r.username)];
    this.confService.getUsers(members).subscribe(
      users => {
        this.users = users;
      }    );
  }

  public addUserToProject(user: string, role: string) {
    const member: ProjectMemberDto = new ProjectMemberDto(this.project.projectKey, user, role);
    this.confService.addMemberToProject(member)
      .subscribe(() => {
          this.project.members.push(member);
          this.ngOnInit();
        }
      );
  }

  public removeMemberFromProject(member: ProjectMemberDto) {
    this.confService.removeMemberFromProject(member.username, member.projectKey)
      .subscribe(() => {
        this.project.members.splice(this.project.members.indexOf(member), 1);
        this.ngOnInit();
      }, error => {
        if (error.status !== 200) {
          this.error = 'You cannot remove project owner!';
        }
      });
  }

  public chooseIcon(files: FileList) {
    this.icon = files.item(0);
    this.loading = true;
    this.validIcon = true;


  }

  public acceptRequest(user: string, role: string) {
    let member: ProjectMemberDto = new ProjectMemberDto(this.project.projectKey, user, role);
    this.confService.acceptRequestForAccess(member)
      .subscribe(data => {
          this.project.members.push(member);
          this.project.requests.splice(this.project.requests.indexOf(member), 1);
          this.ngOnInit();
        }
      );
  }

  public declineRequest(request: ProjectMemberDto) {
    this.confService.declineRequestForAccess(this.project.projectKey, request.username).subscribe(
      () => {
        this.project.requests.splice(this.project.requests.indexOf(request), 1);
        this.ngOnInit();
      }
    );
  }

  public goToIssueConfiguration() {
    this.router.navigate(['/project/'+this.project.projectKey+'/configuration/issues']);
  }
}
