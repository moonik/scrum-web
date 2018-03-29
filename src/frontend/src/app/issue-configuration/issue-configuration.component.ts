import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { IssueConfigurationService } from './issue-configuration.service';

@Component({
  templateUrl: './issue-configuration.component.html',
  styleUrls: ['./issue-configuration.component.css'],
  providers: [IssueConfigurationService]
})
export class IssueConfigurationComponent {

  projectKey = '';
  issueTypes = [];
  oldTypes = '';

  constructor(private activatedRoute: ActivatedRoute, private issueConfService: IssueConfigurationService) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.projectKey = params['projectKey'];
    });
    this.issueConfService.getIssueTypes(this.projectKey)
      .subscribe( data => {
        this.issueTypes = data;
        this.oldTypes = JSON.stringify(data);
      });
  }
}
