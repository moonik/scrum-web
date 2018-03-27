import {Component, OnInit} from '@angular/core';
import {ProjectDto} from '../model/projectDto';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ProjectService} from './project.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  public projectDto: ProjectDto = new ProjectDto();
  public projectForm: FormGroup;
  public error = '';
  public icon: File = null;
  public loading = false;
  public validIcon = true;

  constructor(fb: FormBuilder,
              private router: Router,
              private projectService: ProjectService) {

    this.projectForm = fb.group({
      name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
      description: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(300)]],
      projectKey: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(8)]],
      icon: [null]
    });

  }

  ngOnInit() {
  }

  createProject() {
    this.projectService.createProject(this.projectDto)
      .subscribe(
        () => {
          this.router.navigate(['/home']);
        },
        error => {
          if (error.status === 409) {
            this.error = error._body;
          }
        });
  }

  chooseIcon(files: FileList) {
    this.icon = files.item(0);
    this.loading = true;
    this.validIcon = true;
    console.log('load icon');
    this.loading = false;
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
}
