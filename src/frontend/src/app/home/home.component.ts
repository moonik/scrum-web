import { Component, OnInit } from '@angular/core';
import {ProjectDto} from "../model/projectDto";
import { HttpClient } from "../shared/http.client.service";
import {HomeService} from "./home.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  projectDto: ProjectDto = new ProjectDto();
  projects: ProjectDto[] = [];

  constructor(private _http: HttpClient, private homeService: HomeService) {
    this.getAllOwnProjects();
  }

  ngOnInit() {
  }

  getAllOwnProjects() {
    this.homeService.getAllOwnProjects().map(res => res.json())
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }

}
