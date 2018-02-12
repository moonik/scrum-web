import { Component, OnInit } from '@angular/core';
import {ProjectDto} from "../model/projectDto";
import { HttpClient } from "../shared/http.client.service";
import {HomeService} from "./home.service";
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  projects: ProjectDto[] = [];

  constructor(private _homeService: HomeService, private _router: Router) {
    this.getAllOwnProjects();
  }

  ngOnInit() {
  }

  getAllOwnProjects() {
    this._homeService.getAllOwnProjects()
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }

  goToProjectDetails(projectKey: string) {
    this._router.navigate(['project/details/'+projectKey]);
  }
}
