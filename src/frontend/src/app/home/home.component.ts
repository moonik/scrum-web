import { Component, OnInit } from '@angular/core';
import {ProjectDto} from "../model/projectDto";
import { HttpClient } from "../shared/http.client.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  projectDto: ProjectDto = new ProjectDto();
  projects = [];

  constructor(private _http: HttpClient) {
    this.getAllOwnProjects();
  }

  ngOnInit() {
  }

  getAllOwnProjects() {
    this._http.get('/api/scrum-web/project/all').map(res => res.json())
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }

}
