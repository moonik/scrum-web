import { Component, OnInit } from '@angular/core';
import { ProjectFieldsCollector } from '../model/project-fields/ProjectFieldsCollector';
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
    console.log(this.projectFieldsCollector);
    this.fields.push({id: id, submitted: false, elements: []});
  }

  public addFieldElement(field: any, id: number) {
    field.elements.push({id: id});
  }

  public removeField(field: any, fieldType: string) {
    let index = this.fields.indexOf(field);
    this.fields.splice(index, 1);
    if (this.projectFieldsCollector) {
      this.removeFieldFromCollector(field, this.convertFieldTypeToField(fieldType));
    }
  }

  public submitField(formData: any, field: any) {
    field.submitted = true;
    formData.elements = field.elements;
    let fieldType = this.convertFieldTypeToField(formData.fieldType);
    if (!this.projectFieldsCollector) {
      this.projectFieldsCollector = new ProjectFieldsCollector();
    }
    let createdField = this._fieldCreator.createField(formData, field.id);
    let index = this.findField(fieldType, createdField);
    if (index !== -1) {
      return this.projectFieldsCollector[fieldType][index] = createdField;
    } else
      return this.projectFieldsCollector[fieldType].push(createdField);
  }

  private findField(fieldType: string, field) {
    return this.projectFieldsCollector[fieldType]
      .findIndex(f => f.id === field.id);
  }
  
  private removeFieldFromCollector(field: any, fieldType: string) {
    let index = this.findField(fieldType, field);
    this.projectFieldsCollector[fieldType].splice(index, 1);
  }

  public editField(field) {
    field.submitted = false;
  }

  public isValidGeneralData(formData) {
    return formData.fieldType != '' && formData.fieldName != '';
  }

  public isValidAdditionalDataInputField(formData) {
    return formData.maxChars != '' && formData.minChars != '';
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
