import {Component, OnInit} from '@angular/core';
import {ProjectDto} from '../model/projectDto';
import {HomeService} from './home.service';
import {Router} from '@angular/router';
import {StorageService} from "../shared/storage.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  projects: ProjectDto[] = [];

  constructor(private homeService: HomeService,
              private router: Router,
              private storage: StorageService) {
  }

  ngOnInit() {
    this.getAllOwnProjects();
  }

  getAllOwnProjects() {
    this.homeService.getAllOwnProjects()
      .subscribe(
        data => {
          console.log('projects: ' + data[0].members[0].username);
          this.projects = data;
          console.log(this.projects[0].members[0].username);
        }
      );
  }

  onConfigureProject(project: ProjectDto) {
    this.storage.setScope(project);
    console.log(project.members[0].username);
    this.router.navigate(['project', project.projectKey]);
  }

}
