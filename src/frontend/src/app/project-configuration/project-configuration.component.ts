import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserDto} from '../model/userDto';
import {ProjectConfigurationService} from './project-configuration.service';
import {ProjectDto} from '../model/projectDto';
import {StorageService} from '../shared/storage.service';
import {ProjectMemberDto} from '../model/projectMemberDto';

import * as roles from '../constants/roles';
import {FileUploadService} from '../shared/file-upload.service';

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
  selectedRole: string;
  error: string;
  icon: File = null;
  loading = false;
  goodIcon = true;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private confService: ProjectConfigurationService,
              private storageService: StorageService,
              private uploadService: FileUploadService) {
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
    const members: string[] = this.project.members.map(m => m.username);
    this.confService.getUsers(members).subscribe(
      users => this.users = users
    );
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
        if (error.status === 418) {
          this.error = 'You cannot remove project owner!';
        }
      });
  }

  chooseIcon(files: FileList) {
    this.icon = files.item(0);
    this.loading = true;
    this.goodIcon = true;
    const oldIcon = this.project.icon;

    this.uploadService.uploadFile(this.icon)
      .subscribe(data => {
          this.project.icon = data.json()['link'];
          this.confService.changeProjectIcon(this.project.projectKey, this.project.icon).subscribe();
          if (oldIcon !== this.project.icon) {
            this.uploadService.deleteFile(oldIcon).subscribe();
          }
          this.loading = false;
        },
        error => {
          if (error.status !== 200) {
            this.goodIcon = false;
          }
        });
  }
}
