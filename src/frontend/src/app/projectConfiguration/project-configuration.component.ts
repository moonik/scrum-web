import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {UserDto} from '../model/userDto';
import {ProjectConfigurationService} from "./project-configuration.service";
import {Subscription} from "rxjs/Subscription";
import {ProjectDto} from "../model/projectDto";
import {StorageService} from "../shared/storage.service";

@Component({
  selector: 'app-project-configuration',
  templateUrl: './project-configuration.component.html',
  styleUrls: ['./project-configuration.component.css']
})

export class ProjectConfigurationComponent implements OnInit {

  users: UserDto[] = [];
  project: ProjectDto = new ProjectDto();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private confService: ProjectConfigurationService,
              private storageService: StorageService) {

    console.log("cycki");
  }

  ngOnInit() {
    this.project = this.storageService.getScope();
    // this.route.queryParams.subscribe(
    //   params => {
    //     console.log(params);
    //     console.log('account: ' + params['members'][0].username);
    //     console.log(params.members);
    //     this.project.projectKey = params.projectKey;
    //     this.project.description = params.description;
    //     this.project.name = params.name;
    //     for (let i in params.members) {
    //       console.log(params.members[i].username);
    //       // this.project.members.push(params.members[i]);
    //     }
    //     this.project.members = params.members.username;
    //   }
    // );
    this.getAllUsers();
  }

  getAllUsers() {
    this.confService.getUsers(this.project).subscribe(
      users => this.users = users
    );
  }

  addUserToProject(user: UserDto) {

  }

}
