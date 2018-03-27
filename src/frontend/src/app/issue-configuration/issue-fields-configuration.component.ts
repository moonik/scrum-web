import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { CheckBoxContainerDto } from '../model/project-fields/CheckBoxContainerDto';
import { CheckBoxDto } from '../model/project-fields/CheckBoxDto';
import { InputFieldDto } from '../model/project-fields/InputFieldDto';
import { ListElementsContainerDto } from '../model/project-fields/ListElementsContainerDto';
import { ListElementDto } from '../model/project-fields/ListElementDto';
import { RadioButtonContainerDto } from '../model/project-fields/RadioButtonContainerDto';
import { RadioButtonDto } from '../model/project-fields/RadioButtonDto';
import { TextAreaDto } from '../model/project-fields/TextAreaDto';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FieldCreator } from './field-creator';
import { IssueConfigurationService } from './issue-configuration.service';

import * as fieldTypes from '../constants/field-type';
import { ProjectFieldsCollector } from '../model/project-fields/ProjectFieldsCollector';

@Component({
  selector: 'app-issue-fields-configuration',
  templateUrl: './issue-fields-configuration.component.html',
  styleUrls: ['./issue-configuration.component.css'],
  providers: [FieldCreator, IssueConfigurationService]
})
export class IssueFieldsConfigurationComponent implements OnInit, OnChanges {
  public fields = [];
  public oldFields = '';
  public fieldTypes = fieldTypes.default;
  public fieldTypesArray = Object.values(this.fieldTypes);
  public chooseIssueType = 'Choose issue type...';
  public chosenIssueType = '';
  @Input()
  public issueTypes;
  @Input()
  private projectKey;

  constructor(private _activatedRoute: ActivatedRoute,
              private _fieldCreator: FieldCreator,
              private _issueConfService: IssueConfigurationService) {}

  ngOnInit() {}

  ngOnChanges(changes) {
    this.fetchFields();
  }

  public fetchFields() {
    if (this.chosenIssueType && this.chosenIssueType !== this.chooseIssueType) {
      this._issueConfService.getProjectFields(this.projectKey, this.chosenIssueType)
      .subscribe(data => { this.fields = data; this.oldFields = JSON.stringify(data); });
    }
  }

  public addField() {
    this.fields.push({id: null, submitted: false, elements: []});
  }

  public showAddFieldButton() {
    return this.chosenIssueType !== this.chooseIssueType && this.chosenIssueType !== '';
  }

  public addFieldElement(field: any, id: number) {
    field.elements.push({id: id});
  }

  public removeField(field: any, fieldType: string) {
    const index = this.fields.indexOf(field);
    this.fields.splice(index, 1);
    if (field.id != null) {
      this._issueConfService.removeField(field.id, this.projectKey, this.chosenIssueType)
        .subscribe(data => { this.fields = this.fields.concat(data); this.oldFields = JSON.stringify(data); });
    }
  }

  public removeFieldElement(field: any, element: any) {
    const index = this.fields.indexOf(element);
    field.elements.splice(index, 1);
  }

  public submitField(field: any) {
    field.submitted = true;
  }

  public editField(field: any) {
    field.submitted = false;
  }

  public isValidGeneralData(formData: any) {
    return (formData.fieldType && formData.fieldName) && (formData.fieldType !== '' && formData.fieldName !== '');
  }

  public isParamsElements(fieldType: string) {
    return fieldType !== this.fieldTypes.inputField && fieldType !== this.fieldTypes.textArea && fieldType;
  }

  public isParamsTextField(fieldType: string) {
    return fieldType === this.fieldTypes.inputField || fieldType === this.fieldTypes.textArea;
  }

  public getFieldElement(fieldType: string) {
    if (fieldType === this.fieldTypes.checkBox) {
      return 'checkbox';
    } else if (fieldType === this.fieldTypes.list) {
      return 'list element';
    } else if (fieldType === this.fieldTypes.radioButton) {
      return 'radio button';
    }
  }

  public canSubmit() {
    return this.fields.length > 0 && this.compare() && this.checkSumbitted();
  }

  public createFields() {
    if (this.filterOut().length > 0) {
      this._issueConfService.createFields(this.collectFields(), this.projectKey, this.chosenIssueType)
        .subscribe(data => { this.fields = data; this.oldFields = JSON.stringify(data); });
      this._fieldCreator.projectFieldsCollector = new ProjectFieldsCollector();
    }
  }

  private checkSumbitted() {
    return this.fields.filter(field => field.submitted).length === this.fields.length;
  }

  private filterOut() {
    return this.fields.filter(field => field.id === null || this.searchField(field) === true);
  }

  private searchField(field: any) {
    return JSON.parse(this.oldFields).filter(f => JSON.stringify(f) !== JSON.stringify(field)).length > 0;
  }

  private compare() {
    return this.oldFields !== JSON.stringify(this.fields);
  }

  private collectFields() {
    this.fields.map(field => this._fieldCreator.createField(field));
    return this._fieldCreator.projectFieldsCollector;
  }
}
