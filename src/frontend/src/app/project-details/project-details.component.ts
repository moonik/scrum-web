import {Component, OnInit, HostListener} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {ProjectDetailsService} from './project-details.service';
import {ProjectDetailsDto} from '../model/ProjectDetailsDto';
import {IssueDto} from '../model/IssueDto';
import {IssueService} from '../issue/issue.service';
import {IssueDetailsDto} from '../model/IssueDetailsDto';
import {IssueComment} from '../model/IssueComment';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { SearchService } from '../search/search.service';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css'],
  providers: [ProjectDetailsService, IssueService, SearchService]
})
export class ProjectDetailsComponent implements OnInit {

  projectKey: string;
  projectDetails: ProjectDetailsDto = new ProjectDetailsDto();
  selectedIssue: IssueDetailsDto;
  loading = false;
  commentForm: FormGroup;
  comments: IssueComment[] = [];
  newComment: IssueComment = new IssueComment();
  projectMembers: Array<any> = [];
  hoveredCommentId: number;
  selectedComment: IssueComment = new IssueComment();
  oldContent: string;

  constructor(private activatedRoute: ActivatedRoute,
              private projectDetailsService: ProjectDetailsService,
              private issueService: IssueService,
              private searchService: SearchService,
              private fb: FormBuilder) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.projectKey = params['projectKey'];
    });
    this.getProjectDetails();

    this.commentForm = fb.group({
      content: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]]
    });
  }

  ngOnInit() {
    this.getProjectDetails();
    this.getAssignees();
  }

  selectIssue(issueKey: string) {
    this.loading = true;
    this.issueService.getIssueDetails(issueKey)
      .subscribe(
        data => {
          this.selectedIssue = data;
          this.loading = false;
          this.getIssueComments();
        });
  }

  getProjectDetails() {
    this.loading = true;
    return this.projectDetailsService.getProjectDetails(this.projectKey)
      .subscribe(data => {
        this.projectDetails = data;
        if (data.issues.length > 0) {
          this.selectIssue(data.issues[0].issueKey);
        }
        this.loading = false;
      });
  }

  showIssueList() {
    return this.projectDetails.issues.length > 0;
  }

  onIssueCreate(issueDto: IssueDto) {
    const length = this.projectDetails.issues.length + 1;
    issueDto.issueKey = this.projectDetails.projectDto.projectKey + '-' + length;
    this.projectDetails.issues.unshift(issueDto);
    this.selectIssue(issueDto.issueKey);
  }

  onAssignToIssue(username: string) {
    if (!username) {
      username = localStorage.getItem('currentUser');
    }
    this.issueService.assignToIssue(this.selectedIssue.key, username)
      .subscribe(() => {
        this.ngOnInit();
      });
  }

  checkAssignees(): boolean {
    return !this.searchService.findUserInAssignees(this.selectedIssue.assignees) &&
      this.searchService.findUserInMembers(this.projectDetails.projectDto.members);
  }

  isOwner(): boolean {
    return this.selectedIssue.reporter.username === localStorage.getItem('currentUser');
  }

  getAssignees() {
    return this.issueService.getAssignees(this.projectKey)
      .subscribe(data => this.projectMembers = data);
  }

  onRemoveFromAssign(username: string) {
    this.issueService.unAssignFromIssue(username, this.selectedIssue.key)
      .subscribe(() => this.ngOnInit());
  }

  isAssigned(username: string) {
    return !this.selectedIssue.assignees.map(a => a.username).includes(username);
  }

  getIssueComments() {
    return this.issueService.getIssueComments(this.selectedIssue.key)
      .subscribe(
        data => {
          this.comments = data;
        }
      );
  }

  addComment() {
    return this.issueService.addComment(this.selectedIssue.key, this.newComment)
      .subscribe(
        data => {
          this.comments.push(data);
          this.newComment.content = '';
        }
      );
  }

  deleteComment(comment: IssueComment) {
    return this.issueService.deleteComment(comment.id)
      .subscribe(
        () => {
          const index = this.comments.indexOf(comment);
          this.comments.splice(index, 1);
        }
      );
  }

  editComment(comment: IssueComment, commentId: number) {
    return this.issueService.editComment(commentId, comment)
      .subscribe();
  }

  getCurrentUser(): string {
    return localStorage.getItem('currentUser');
  }

  mouseEnter(comment: any) {
    this.hoveredCommentId = comment.id;
    comment.hover = true;
  }

  mouseLeave(comment: any) {
    comment.hover = false;
  }

  checkCommentLength(): boolean {
    return this.commentForm.controls.content.errors.minlength || this.commentForm.controls.content.errors.maxlength;
  }

  checkControl(name: string): boolean {
    return this.commentForm.controls[name].invalid && (this.commentForm.controls[name].touched || this.commentForm.controls[name].dirty);
  }

  checkIfEdited(comment: any) {
    return this.oldContent !== comment.content;
  }

  clickInside($event: Event, comment: any){
    $event.preventDefault();
    $event.stopPropagation();  // <- that will stop propagation on lower layers
    this.oldContent = comment.content;
    this.selectedComment = comment;
    this.selectedComment.editing = true;
  }

  @HostListener('document:click', ['$event']) clickedOutside($event){
    this.selectedComment.editing = false;
    if (this.checkIfEdited(this.selectedComment)) {
      this.editComment(this.selectedComment, this.selectedComment.id);
    }
  }
}
