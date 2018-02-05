import { Component, OnInit } from '@angular/core';
import {ProjectDto} from "../model/projectDto";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthenticationService} from "../security/authentication.service";
import {Http, Headers} from "@angular/http";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  projectDto: ProjectDto = new ProjectDto();
  projectForm: FormGroup;

  headers: Headers = new Headers();


  constructor(private authenticationService: AuthenticationService, fb: FormBuilder, private router: Router, private http: Http) {

    this.projectForm = fb.group({
      name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
      description: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(300)]]

    });
    this.headers.append('Authorization', this.authenticationService.token);
    this.headers.append('Content-Type', 'application/json');
  }

  ngOnInit() {
  }

  createproject(){
    console.log(this.authenticationService.headers);
    return this.http.post('/api/scrum-web/project/create',JSON.stringify(this.projectDto),{headers: this.headers})
      .subscribe(projectDto => this.projectDto = projectDto.json())
  }

  checkLogin(): boolean{
    return localStorage.getItem('currentUser') ? true : false;
  }

  getCurrentUser(): string{
    let Username = JSON.parse(localStorage.getItem('currentUser'));
    return Username.username;
  }

  getCurrentToken(): string{

    return this.authenticationService.token;
  }



}
