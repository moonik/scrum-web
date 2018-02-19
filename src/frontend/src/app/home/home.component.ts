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

  projects: ProjectDto[] = [];

  constructor(private homeService: HomeService) {
    this.getAllOwnProjects();
  }

  ngOnInit() {
  }

  getAllOwnProjects() {
    this.homeService.getAllOwnProjects()
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }

}
