import { Component, OnInit } from '@angular/core';
import {ProjectDto} from '../model/projectDto';
import { HttpClient } from '../shared/http.client.service';
import {HomeService} from './home.service';
import {FileUploadService} from '../shared/file-upload.service';
import {forEach} from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  projects: ProjectDto[] = [];
  imageToShow: any;

  constructor(private homeService: HomeService,
              private uploadService: FileUploadService) {
  }

  ngOnInit() {
    this.getAllOwnProjects();
  }

  getAllOwnProjects() {
    this.homeService.getAllOwnProjects()
      .subscribe(
        data => {
          this.projects = data;
          for (let i = 0; i < this.projects.length; i++) {
            this.loadIcon(this.projects[i].icon, i);
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
    const f = filename.substr(filename.lastIndexOf('/') + 1);
    console.log(f);
    return this.uploadService.loadFile(f)
      .subscribe(
        data => {
          this.createImageFromBlob(data, i);
        }
      );
  }
}
