import { Component, TemplateRef, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { IssueConfigurationService } from './issue-configuration.service';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
    selector: 'app-issue-type-configuration',
    templateUrl: './issue-type-configuration.component.html',
    styleUrls: ['./issue-configuration.component.css'],
    providers: [IssueConfigurationService]
  })
  export class IssueTypeConfigurationComponent {

    @Input()
    public types;
    @Input()
    private projectKey: string;

    constructor(private _service: IssueConfigurationService) {}

    public addNewType() {
      this.types.push({id: null});
    }

    public removeType(type: any) {
      let index = this.types.indexOf(type);
      this.types.splice(index, 1);
    }

    public createType() {
      this._service.createType(this.filterOut(), this.projectKey)
        .subscribe();

    }

    public filterOut() {
      return this.types.filter(t => t.id === null || !t.isDefault);
    }

    public isDisabled(type: any) {
      return type.isDefault && type.id !== null ? '' : null;
    }

    public isSubmitDisabled(type: any) {
      return this.filterOut().length === 0 && type.issueType === '';
    }

    public isValid() {
      console.log(this.types.filter(type => type.issueType === '').length > 0);
      return this.types.filter(type => type.issueType === '').length > 0;
    }
  }