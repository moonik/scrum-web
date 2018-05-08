import {Component, OnInit} from '@angular/core';
import {ProjectDto} from '../model/ProjectDto';
import {HomeService} from './home.service';
import {Router} from '@angular/router';
import {StorageService} from '../shared/storage.service';
import {NotificationService} from '../shared/notification.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  projects: ProjectDto[] = [];
  stompClient;

  constructor(private homeService: HomeService,
              private router: Router,
              private storage: StorageService,
              private nt: NotificationService) {
    this.getAllProjects();
  }

  ngOnInit() {
    this.getAllProjects();
  }

  getAllProjects() {
    this.homeService.getAllProjects()
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }

  onConfigureProject(project: ProjectDto) {
    this.storage.setScope(project);
    this.router.navigate(['project/configure', project.projectKey]);
  }

  goToProjectDetails(projectKey: string) {
    this.router.navigate(['project/details/' + projectKey]);
  }

  getCurrentUser(): string {
    return localStorage.getItem('currentUser');
  }

  isOwner(owner: string): boolean {
    return owner === this.getCurrentUser();
  }

}
