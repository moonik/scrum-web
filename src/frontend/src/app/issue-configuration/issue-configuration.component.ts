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
  public issueTypes: Array<string> = []
  private projectKey = '';

  constructor(private _activatedRoute: ActivatedRoute, private _fieldCreator: FieldCreator, private _issueConfService: IssueConfigurationService) {
    this._activatedRoute.params.subscribe((params: Params) => {
        this.projectKey = params['projectKey'];
    });
  }

  ngOnInit() {
    this._issueConfService.getIssueTypes(this.projectKey)
      .subscribe(data => this.issueTypes = data);
  }

  public addField(id: number) {
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

  public removeFieldElement(field: any, element: any) {
    let index = this.fields.indexOf(element);
    field.elements.splice(index, 1);
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

  private findField(fieldType: string, field: any) {
    return this.projectFieldsCollector[fieldType]
      .findIndex(f => f.id === field.id);
  }
  
  private removeFieldFromCollector(field: any, fieldType: string) {
    let index = this.findField(fieldType, field);
    this.projectFieldsCollector[fieldType].splice(index, 1);
  }

  public editField(field: any) {
    field.submitted = false;
  }

  public isValidGeneralData(formData: any) {
    return formData.fieldType != '' && formData.fieldName != '';
  }

  public isValidAdditionalDataInputField(formData: any) {
    return formData.maxChars != '' && formData.minChars != '';
  }

  public isParamsElements(fieldType: string) {
    return fieldType !=='INPUT_FIELD' && fieldType !=='TEXT_AREA' && fieldType!=='';
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

  public createFields(issueType: string) {
    return this._issueConfService.createFields(this.projectFieldsCollector, this.projectKey, issueType)
      .subscribe();
  }
}
