import { Component, OnInit } from '@angular/core';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {ProjectDetailsService} from './project-details.service';
import {ProjectDetailsDto} from '../model/ProjectDetailsDto';
import {IssueDto} from '../model/IssueDto';
import {IssueService} from '../issue/issue.service';
import {IssueDetailsDto} from '../model/IssueDetailsDto';
import {IssueComment} from "../model/IssueComment";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

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
  public issueId: number;
  public comments: IssueComment[] = [];
  public commentForm: FormGroup;

  constructor(private _activatedRoute: ActivatedRoute, private _projectDetailsService: ProjectDetailsService,
    private _issueService: IssueService, fb: FormBuilder) {
    this._activatedRoute.params.subscribe((params: Params) => {
        this.projectKey = params['projectKey'];
    });
    this.getProjectDetails();

    this.commentForm = fb.group({
      content:[null, [Validators.required]]
    });

  }

  ngOnInit() {}

  public selectIssue(issueId: number) {
    this.loading = true;
    this._issueService.getIssueDetails(issueId)
      .subscribe(
        data => {
          this.selectedIssue = data;
          this.issueId = data.id;
          this.loading = false;
          this.getIssueComments(this.issueId)
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

  public getIssueComments(issueId: number) {
    return this._issueService.getIssueComments(issueId)
      .subscribe(
        data => {
          this.comments = data;
        }
      )
  }

  public addcomment(content: string, issueId: number){
    return this._issueService.addComment(issueId, content)
  }

}
