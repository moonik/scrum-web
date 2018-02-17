import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {ProjectDetailsService} from './project-details.service';
import {ProjectDetailsDto} from '../model/ProjectDetailsDto';
import {IssueDto} from '../model/IssueDto';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css'],
  providers: [ProjectDetailsService]
})
export class ProjectDetailsComponent implements OnInit {

  public projectKey: string;
  public projectDetails: ProjectDetailsDto;
  public selectedIssue: IssueDto;

  constructor(private _activatedRoute: ActivatedRoute, private _projectDetailsService: ProjectDetailsService) {
    this._activatedRoute.params.subscribe((params: Params) => {
        this.projectKey = params['projectKey'];
    });
    this.getProjectDetails();
  }

  ngOnInit() {}

  public selectIssue(issueId: number) {
    console.log('HAYA');
    this.selectedIssue = this.projectDetails.issues.find(issue => issue.id === issueId);
  }

  public getProjectDetails() {
    return this._projectDetailsService.getProjectDetails(this.projectKey)
      .subscribe( data => {
          this.projectDetails = data;
          this.selectedIssue = data.issues[0];
        });
  }

  public showIssueList() {
    return this.projectDetails && this.projectDetails.issues.length > 0;
  }
}
