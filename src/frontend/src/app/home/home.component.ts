import {Component, OnInit} from '@angular/core';
import {ProjectDto} from '../model/projectDto';
import {HomeService} from './home.service';
import {FileUploadService} from '../shared/file-upload.service';
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
              private storage: StorageService,
              private uploadService: FileUploadService) {
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
          for (let i = 0; i < this.projects.length; i++) {
            if (this.projects[i].icon != null) {
              this.loadIcon(this.projects[i].icon, i);
            }
          }
        }
      );
  }

  createImageFromBlob(image: Blob, i: number) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.projects[i].image = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  loadIcon(filename: string, i: number) {
    return this.uploadService.loadFile(filename)
      .subscribe(
        data => {
          this.createImageFromBlob(data, i);
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
