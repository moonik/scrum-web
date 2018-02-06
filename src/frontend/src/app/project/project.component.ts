import { Component, OnInit } from '@angular/core';
import {ProjectDto} from "../model/projectDto";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import { ProjectService } from "./project.service";
import { HttpClient } from "../shared/http.client.service";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  projectDto: ProjectDto = new ProjectDto();
  projectForm: FormGroup;
  error = '';

  constructor(fb: FormBuilder, private router: Router, private projectService: ProjectService, httpClient: HttpClient) {

    this.projectForm = fb.group({
      name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
      description: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(300)]],
      projectKey: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(8)]]
    });

  }

  ngOnInit() {
  }

  createproject(){
    this.projectService.createProject(this.projectDto)
      .subscribe(
        success => {
          this.router.navigate(['/home']);
        },
        error => {
          if(error.status === 409){
            this.error = 'Project with key ' + this.projectDto.projectKey + ' already exists!';
          }
        });
  }

  checkProjectNameLength(): boolean {
    return this.projectForm.controls.name.errors.minlength || this.projectForm.controls.name.errors.maxlength;
  }

  checkProjectKeyLength(): boolean {
    return this.projectForm.controls.projectKey.errors.minlength || this.projectForm.controls.projectKey.errors.maxlength;
  }

  checkDescriptionLength(): boolean {
    return this.projectForm.controls.description.errors.minlength || this.projectForm.controls.description.errors.maxlength;
  }

  checkControl(name: string): boolean {
    return this.projectForm.controls[name].invalid && (this.projectForm.controls[name].touched || this.projectForm.controls[name].dirty);
  }

  checkLogin(): boolean{
    return localStorage.getItem('currentUser') ? true : false;
  }

  getCurrentUser(): string{
    let Username = JSON.parse(localStorage.getItem('currentUser'));
    return Username.username;
  }

}
