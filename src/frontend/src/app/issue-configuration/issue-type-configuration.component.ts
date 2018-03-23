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
    public oldTypes;
    @Input()
    private projectKey: string;

    constructor(private _service: IssueConfigurationService) {}

    public addNewType() {
      this.types.push({id: null});
    }

    public removeType(type: any) {
      if (!type.isDefault) {
        if (type.id) {
          this._service.deleteType(type.id).subscribe();
        }
        let index = this.types.indexOf(type);
        this.types.splice(index, 1);
      }
    }

    public createType() {
      this._service.createType(this.filterOut(), this.projectKey)
        .subscribe();

    }

    public filterOut() {
      return this.types.filter(t => t.id === null).filter(t => this.findType(t) === true);
    }

    public isDisabled(type: any) {
      return type.isDefault ? '' : null;
    }

    public isValid() {
      let filteredEmpty = this.filterOutEmptyTypes();
      let filteredNotEdited = this.filterOut();
      return filteredEmpty.length === 0 && filteredNotEdited.length > 0;
    }

    public isEdited() {
      return JSON.stringify(this.oldTypes) === JSON.stringify(this.types);
    }

    private filterOutEmptyTypes() {
      return this.types.filter(type => type.issueType === '' || !type.issueType);
    }

    private filterOutNotChanged() {
      return this.types.filter(t => this.findType(t))
    }

    private findType(type: any) {
      return this.oldTypes.filter(t => JSON.stringify(t) !== JSON.stringify(type)).length > 0;
    }
  }