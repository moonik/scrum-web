import { Component, OnInit } from '@angular/core';
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
  selector: 'app-issue-configuration',
  templateUrl: './issue-configuration.component.html',
  styleUrls: ['./issue-configuration.component.css'],
  providers: [FieldCreator, IssueConfigurationService]
})
export class IssueConfigurationComponent implements OnInit {
  public fields = [];
  public fieldTypes = fieldTypes.default;
  public fieldTypesArray = Object.values(this.fieldTypes);
  private issueType = '';
  private projectKey = '';

  constructor(private _activatedRoute: ActivatedRoute, private _fieldCreator: FieldCreator, private _issueConfService: IssueConfigurationService) {
    this._activatedRoute.params.subscribe((params: Params) => {
        this.projectKey = params['projectKey'];
        this.issueType = params['issueType']
    });
  }

  ngOnInit() {
    this._issueConfService.getProjectFields(this.projectKey, this.issueType)
      .subscribe(data => this.fields = data);
  }

  public addField(id: number) {
    this.fields.push({id: null, submitted: false, elements: []});
  }

  public addFieldElement(field: any, id: number) {
    field.elements.push({id: id});
  }

  public removeField(field: any, fieldType: string) {
    let index = this.fields.indexOf(field);
    this.fields.splice(index, 1);
  }

  public removeFieldElement(field: any, element: any) {
    let index = this.fields.indexOf(element);
    field.elements.splice(index, 1);
  }

  public submitField(field: any) {
    field.submitted = true;
  }

  public editField(field: any) {
    field.submitted = false;
  }

  public isValidGeneralData(formData: any) {
    return formData.fieldType != '' && formData.fieldName != '';
  }

  public isParamsElements(fieldType: string) {
    return fieldType !=='INPUT_FIELD' && fieldType !=='TEXT_AREA' && fieldType;
  }

  public isParamsTextField(fieldType: string) {
    return fieldType ==='INPUT_FIELD' || fieldType ==='TEXT_AREA';
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

  public submitted() {
    return this.fields.filter(field => field.submitted).length > 0;
  }

  public createFields() {
    this._issueConfService.createFields(this.collectFields(), this.projectKey, this.issueType)
      .subscribe(data => this.fields = data);
    this._fieldCreator.projectFieldsCollector = new ProjectFieldsCollector();
  }

  private collectFields() {
    this.fields.map(field => this._fieldCreator.createField(field));
    return this._fieldCreator.projectFieldsCollector;
  }
}
