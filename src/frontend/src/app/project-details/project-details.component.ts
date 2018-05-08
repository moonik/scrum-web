import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {ProjectDetailsService} from './project-details.service';
import {ProjectDetailsDto} from '../model/ProjectDetailsDto';
import {IssueDto} from '../model/IssueDto';
import {IssueService} from '../issue/issue.service';
import {IssueDetailsDto} from '../model/IssueDetailsDto';
import { SearchService } from '../search/search.service';
import {NotificationService} from '../shared/notification.service';

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
  projectMembers: Array<any> = [];

  constructor(private activatedRoute: ActivatedRoute,
              private projectDetailsService: ProjectDetailsService,
              private issueService: IssueService,
              private searchService: SearchService,
              private notificationService: NotificationService) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.projectKey = params['projectKey'];
    });
    this.getProjectDetails();
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
        const content = localStorage.getItem('currentUser') + ' ' + 'assigned you to issue ' + this.selectedIssue.key;
        this.sendNotification(username, content);
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
      .subscribe(() => {
        const content = localStorage.getItem('currentUser') + ' ' + 'unassigned you from issue ' + this.selectedIssue.key;
        this.sendNotification(username, content);
        this.ngOnInit();
      });
  }

  isAssigned(username: string) {
    return !this.selectedIssue.assignees.map(a => a.username).includes(username);
  }

  private sendNotification(username: string, content: string) {
    if (username !== localStorage.getItem('currentUser')) {
      this.notificationService.sendNotification(username, content);
    }
  }
}
