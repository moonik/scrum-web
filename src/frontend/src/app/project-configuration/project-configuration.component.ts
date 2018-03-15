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
    // let members: string[] = this.project.members.map(m => m.username);
    // const requests: string[] = this.project.requests.map(r => r.username);
    const members = [...this.project.members.map(m => m.username),
      ...this.project.requests.map(r => r.username)];
    // members.concat(this.project.requests.map(r => r.username));
    console.log("members: " + members);
    // console.log("req: " + requests);
    this.confService.getUsers(members).subscribe(
      users => {
        console.log("users: " + users);
        this.users = users;
      }    );
  }

  addUserToProject(user: string, role: string) {
    const member: ProjectMemberDto = new ProjectMemberDto();
    member.projectId = this.project.id;
    member.username = user;
    member.role = role;

    this.confService.addMemberToProject(member)
      .subscribe(data => {
          this.project.members.push(member);
          this.ngOnInit();
        }
      );
  }

  removeMemberFromProject(member: ProjectMemberDto) {
    this.confService.removeMemberFromProject(member.username, member.projectId)
      .subscribe(data => {
          this.project.members.splice(this.project.members.indexOf(member), 1);
          this.ngOnInit();
        }, error => {
        if (error.status === 418) {
          this.error  = 'You cannot remove project owner!';
        }
      });
  }

  createImageFromBlob(image: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.project.image = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  loadIcon(filename: string) {
    return this.uploadService.loadFile(filename)
      .subscribe(
        data => {
          this.createImageFromBlob(data);
        }
      );
  }

  chooseIcon(files: FileList) {
    this.icon = files.item(0);
    this.loading = true;
    this.goodIcon = true;
    const oldIcon = this.project.icon;

    this.uploadService.uploadFile(this.icon)
      .subscribe(data => {
          const link = data.json()['link'];
          this.project.icon = link.substr(link.lastIndexOf('/') + 1);
          this.confService.changeProjectIcon(this.project.projectKey, this.project.icon).subscribe();
          this.loadIcon(this.project.icon);
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
