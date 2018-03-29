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
  types;
  @Input()
  oldTypes;
  @Input()
  private projectKey: string;

  constructor(private _service: IssueConfigurationService) {}

  addNewType() {
    this.types.push({id: null});
  }

  removeType(type: any) {
    if (!type.isDefault) {
      if (type.id) {
        this._service.deleteType(type.id).subscribe();
      }
      const index = this.types.indexOf(type);
      this.types.splice(index, 1);
    }
  }

  createType() {
    this._service.createType(this.filterOut(), this.projectKey)
      .subscribe(
        data => {
          this.types = data;
          this.oldTypes = JSON.stringify(data);
        }
      );
  }

  filterOut() {
    return this.types.filter(t => t.id === null || this.findType(t) === true && !t.isDefault);
  }

  isDisabled(type: any) {
    return type.isDefault ? '' : null;
  }

  isValid() {
    const filteredNotEdited = this.filterOut();
    return filteredNotEdited.length > 0 && this.isEdited();
  }

  isEdited() {
    return this.oldTypes !== JSON.stringify(this.types);
  }

  private filterOutNotChanged() {
    return this.types.filter(t => this.findType(t))
  }

  private findType(type: any) {
    return JSON.parse(this.oldTypes).filter(t => JSON.stringify(t) !== JSON.stringify(type)).length > 0;
  }
}