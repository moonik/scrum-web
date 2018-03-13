import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {ProjectDetailsService} from './project-details.service';
import {ProjectDetailsDto} from '../model/ProjectDetailsDto';
import {IssueDto} from '../model/IssueDto';
import {IssueService} from '../issue/issue.service';
import {IssueDetailsDto} from '../model/IssueDetailsDto';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css'],
  providers: [ProjectDetailsService, IssueService]
})
export class ProjectDetailsComponent implements OnInit {

  public projectKey: string;
  public projectDetails: ProjectDetailsDto = new ProjectDetailsDto();
  public selectedIssue: IssueDetailsDto;
  public loading: boolean = false;

  constructor(private _activatedRoute: ActivatedRoute, private _projectDetailsService: ProjectDetailsService,
    private _issueService: IssueService) {
    this._activatedRoute.params.subscribe((params: Params) => {
        this.projectKey = params['projectKey'];
    });
    this.getProjectDetails();
  }

  ngOnInit() {}

  public selectIssue(issueId: number) {
    this.loading = true;
    this._issueService.getIssueDetails(issueId)
      .subscribe(
        data => {
          this.selectedIssue = data;
          this.loading = false;
        });
  }

  public getProjectDetails() {
    this.loading = true;
    return this._projectDetailsService.getProjectDetails(this.projectKey)
      .subscribe( data => {
          this.projectDetails = data;
          if (data.issues.length > 0) {
            this.selectIssue(data.issues[0].id);
          }
          this.loading = false;
        });
  }

  public showIssueList() {
    return this.projectDetails.issues.length > 0;
  }

  public onIssueCreate(issueDto: IssueDto) {
    let length = this.projectDetails.issues.length+1;
    issueDto.id = length;
    issueDto.issueKey = this.projectDetails.projectDto.name + '-' + length;
    this.projectDetails.issues.unshift(issueDto);
    this.selectIssue(issueDto.id);
  }
}
