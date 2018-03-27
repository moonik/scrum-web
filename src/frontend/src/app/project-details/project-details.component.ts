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
  public commentForm: FormGroup;
  public comments: IssueComment[] = [];
  public newComment: IssueComment = new IssueComment();
  public selectedComment: number;
  projectMembers: Array<any> = [];

  constructor(private _activatedRoute: ActivatedRoute, private _projectDetailsService: ProjectDetailsService,
    private _issueService: IssueService, fb: FormBuilder) {

    this._activatedRoute.params.subscribe((params: Params) => {
      this.projectKey = params['projectKey'];
    });
    this.getProjectDetails();

    this.commentForm = fb.group({
      content: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(255)]]
    });
  }

  ngOnInit() {
    this.getProjectDetails();
    this.getAssignees();
  }

  public selectIssue(issueId: number) {
    this.loading = true;
    this._issueService.getIssueDetails(issueId)
      .subscribe(
        data => {
          this.selectedIssue = data;
          this.loading = false;
          this.getIssueComments();
        });
  }

  public getProjectDetails() {
    this.loading = true;
    return this._projectDetailsService.getProjectDetails(this.projectKey)
      .subscribe(data => {
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
    const length = this.projectDetails.issues.length + 1;
    issueDto.id = length;
    issueDto.issueKey = this.projectDetails.projectDto.name + '-' + length;
    this.projectDetails.issues.unshift(issueDto);
    this.selectIssue(issueDto.id);
  }

  public getIssueComments() {
    return this._issueService.getIssueComments(this.selectedIssue.id)
      .subscribe(
        data => {
          this.comments = data;
        }
      )
  }

  public addComment(){
    return this._issueService.addComment(this.selectedIssue.id, this.newComment)
      .subscribe(
        data => {
          this.comments.push(data);
          this.newComment.content = '';
        }
      );
  }

  public deleteComment(comment: IssueComment){
    return this._issueService.deleteComment(comment.id, this.selectedIssue.id)
      .subscribe(
        success => {
          this.comments.splice(this.comments.indexOf(comment), 1);
          console.log(this.comments);
        }
      )
  }

  public editComment(comment: IssueComment, commentId: number){
    return this._issueService.editComment(commentId, comment)
      .subscribe(
        success => {
          this.editCommentNo(comment);
        }
      )
  }

  getCurrentUser(): string{
    return localStorage.getItem('currentUser');
  }

  public mouseEnter(comment: any) {
    this.selectedComment = comment.id;
    comment.hover = true;
  }

  public mouseLeave(comment: any) {
    comment.hover = false;
  }

  public checkCommentLength(): boolean {
    return this.commentForm.controls.content.errors.minlength || this.commentForm.controls.content.errors.maxlength;
  }

  public checkControl(name: string): boolean {
    return this.commentForm.controls[name].invalid && (this.commentForm.controls[name].touched || this.commentForm.controls[name].dirty);
  }

  public editCommentYes(comment: any){
    comment.editting = true;
  }
  public editCommentNo(comment: any){
    comment.editting = false;
  }

  onAssignToIssue(username: string) {
    if (!username) {
      username = localStorage.getItem('currentUser');
    }
    this._issueService.assignToIssue(this.selectedIssue.id, username)
      .subscribe(() => {
        this.ngOnInit();
      });
  }

  checkAssignees(): boolean {
    return !this.selectedIssue.assignees.map(a => a.username).includes(localStorage.getItem('currentUser'))
      && this.projectDetails.projectDto.members.map(m => m.username).includes(localStorage.getItem('currentUser'));
  }

  isOwner(): boolean {
    return this.selectedIssue.reporter.username === localStorage.getItem('currentUser');
  }

  getAssignees() {
    return this._issueService.getAssignees(this.projectKey)
      .subscribe(data => this.projectMembers = data);
  }

  onRemoveFromAssign(username: string) {
    this._issueService.unAssignFromIssue(username, this.selectedIssue.id)
      .subscribe(() => this.ngOnInit());
  }

  isAssigned(username: string) {
    return !this.selectedIssue.assignees.map(a => a.username).includes(username);
  }
}
