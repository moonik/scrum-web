import { Component, OnInit } from '@angular/core';
import { ProjectFieldsCollector } from '../model/project-fields/ProjectFieldsCollector';
import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { CheckBoxContainerDto } from '../model/project-fields/CheckBoxContainerDto';
import { InputFieldDto } from '../model/project-fields/InputFieldDto';
import { ListElementsContainerDto } from '../model/project-fields/ListElementsContainerDto';
import { RadioButtonContainerDto } from '../model/project-fields/RadioButtonContainerDto';
import { TextAreaDto } from '../model/project-fields/TextAreaDto';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FieldCreator } from './field-creator';
import { IssueConfigurationService } from './issue-configuration.service';

import * as fieldTypes from '../constants/field-type';

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
  public projectFieldsCollector: ProjectFieldsCollector = null;
  private projectKey = '';

  constructor(private _activatedRoute: ActivatedRoute, private _fieldCreator: FieldCreator, private _issueConfService: IssueConfigurationService) {
    this._activatedRoute.params.subscribe((params: Params) => {
        this.projectKey = params['projectKey'];
    });
  }

  ngOnInit() {}

  public addField(id: number) {
    this.fields.push({id: id, submitted: false});
  }

  public removeField(field: any, fieldType: string) {
    let index = this.fields.indexOf(field);
    this.fields.splice(index, 1);
    if (this.projectFieldsCollector) {
      this.removeFieldFromCollector(field.id, this.convertFieldTypeToField(fieldType));
    }
  }

  public submitField(formData: any, field: any) {
    field.submitted = true;
    this.projectFieldsCollector = new ProjectFieldsCollector();
    return this.projectFieldsCollector[this.convertFieldTypeToField(formData.fieldType)]
      .push(this._fieldCreator.createField(formData, this.fields.length));
  }
  
  private removeFieldFromCollector(fieldId: number, fieldType: string) {
    let index = this.fields.indexOf(fieldId);
    this.projectFieldsCollector[fieldType].splice(index, 1);
  }

  public editField(field) {
    field.submitted = false;
  }

  public isValid(formData) {
    return this.isValidGeneralData;
  }

  public isValidGeneralData(formData) {
    return formData.fieldType != '' && formData.fieldName != '';
  }

  private convertFieldTypeToField(fieldType: string): string {
    if (fieldType === this.fieldTypes.inputField) {
      return 'inputFieldDtos';
    } else if (fieldType === this.fieldTypes.textArea) {
      return 'textAreaDtos';
    } else if (fieldType === this.fieldTypes.checkBox) {
      return 'checkBoxContainerDtos';
    } else if (fieldType === this.fieldTypes.radioButton) {
      return 'radioButtonContainerDtos';
    } else if (fieldType === this.fieldTypes.list) {
      return 'listElementsContainerDtos';
    }
  }

  public createFields() {
    return this._issueConfService.createFields(this.projectFieldsCollector, this.projectKey)
      .subscribe();
  }
}
