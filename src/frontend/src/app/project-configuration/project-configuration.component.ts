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
  public goodIcon = true;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private confService: ProjectConfigurationService,
              private storageService: StorageService) {
  }

  ngOnInit() {
    if (!this.storageService.getScope()) {
      this.router.navigate(['/home']);
    }
    this.project = this.storageService.getScope();

    if (this.project.icon == null) {
      this.goodIcon = false;
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
    const member: ProjectMemberDto = new ProjectMemberDto();
    member.projectId = this.project.id;
    member.username = user;
    member.role = role;

    this.confService.addMemberToProject(member)
      .subscribe(() => {
          this.project.members.push(member);
          this.ngOnInit();
        }
      );
  }

  removeMemberFromProject(member: ProjectMemberDto) {
    this.confService.removeMemberFromProject(member.username, member.projectId)
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
    this.goodIcon = true;


  }

  acceptRequest(user: string, role: string) {
    const member: ProjectMemberDto = new ProjectMemberDto();
    member.projectId = this.project.id;
    member.username = user;
    member.role = role;

    this.confService.acceptRequestForAccess(member)
      .subscribe(data => {
          this.project.members.push(member);
          this.project.requests.splice(this.project.requests.indexOf(member), 1);
          this.ngOnInit();
        }
      );
  }

  declineRequest(request: ProjectMemberDto) {
    this.confService.declineRequestForAccess(request.projectId, request.username).subscribe(
      data => {
        this.project.requests.splice(this.project.requests.indexOf(request), 1);
        this.ngOnInit();
      }
    );

  }
}
