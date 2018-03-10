import {Component, OnInit} from '@angular/core';
import {ProjectDto} from '../model/projectDto';
import {HomeService} from './home.service';
import {Router} from '@angular/router';
import {StorageService} from '../shared/storage.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  projects: ProjectDto[] = [];

  constructor(private _homeService: HomeService,
              private _router: Router,
              private storage: StorageService) {
    this.getAllOwnProjects();
  }

  ngOnInit() {
    this.getAllOwnProjects();
  }

  getAllOwnProjects() {
    this._homeService.getAllOwnProjects()
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }

  onConfigureProject(project: ProjectDto) {
    this.storage.setScope(project);
    this._router.navigate(['project/configure', project.projectKey]);
  }

  goToProjectDetails(projectKey: string) {
    this._router.navigate(['project/details/' + projectKey]);
  }

}
