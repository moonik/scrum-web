import {Component} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {IssueConfigurationService} from './issue-configuration.service';

@Component({
  templateUrl: './issue-configuration.component.html',
  styleUrls: ['./issue-configuration.component.css'],
  providers: [IssueConfigurationService]
})
export class IssueConfigurationComponent {

  public projectKey = '';
  public issueTypes = [];
  public oldTypes = '';

  constructor(private _activatedRoute: ActivatedRoute, private _issueConfService: IssueConfigurationService) {
    this._activatedRoute.params.subscribe((params: Params) => {
      this.projectKey = params['projectKey'];
    });
    this._issueConfService.getIssueTypes(this.projectKey)
      .subscribe(data => {
        this.issueTypes = data;
        this.oldTypes = JSON.stringify(data);
      });
  }
}
