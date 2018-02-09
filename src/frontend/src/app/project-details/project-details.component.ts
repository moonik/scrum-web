import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {ProjectDetailsService} from './project-details.service';
import {ProjectDetailsDto} from '../model/ProjectDetailsDto';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css'],
  providers: [ProjectDetailsService]
})
export class ProjectDetailsComponent implements OnInit {

  public projectKey: string;
  public projectDetails: ProjectDetailsDto;

  constructor(private _activatedRoute: ActivatedRoute, private _projectDetailsService: ProjectDetailsService) {
    this._activatedRoute.params.subscribe((params: Params) => {
        this.projectKey = params['projectKey'];
    });
  }

  ngOnInit() {
    this.getProjectDetails();
  }

  public getProjectDetails() {
    return this._projectDetailsService.getProjectDetails('testN').
        subscribe( data => this.projectDetails = data );
  }
}
