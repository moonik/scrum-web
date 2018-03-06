import { Component, OnInit } from '@angular/core';
import { ProjectFieldsCollector } from '../model/project-fields/ProjectFieldsCollector';
import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { CheckBoxContainerDto } from '../model/project-fields/CheckBoxContainerDto';
import { InputFieldDto } from '../model/project-fields/InputFieldDto';
import { ListElementsContainerDto } from '../model/project-fields/ListElementsContainerDto';
import { RadioButtonContainerDto } from '../model/project-fields/RadioButtonContainerDto';
import { TextAreaDto } from '../model/project-fields/TextAreaDto';
import {Router, ActivatedRoute, Params} from '@angular/router';

import * as fieldTypes from '../constants/field-type';

@Component({
  selector: 'app-issue-configuration',
  templateUrl: './issue-configuration.component.html',
  styleUrls: ['./issue-configuration.component.css']
})
export class IssueConfigurationComponent implements OnInit {

  public fields = [];
  public fieldTypes = fieldTypes.default;
  public fieldTypesArray = Object.values(this.fieldTypes);
  public projectFieldsCollector: ProjectFieldsCollector = null;

  private projectKey = '';

  constructor(private _activatedRoute: ActivatedRoute) {
    this._activatedRoute.params.subscribe((params: Params) => {
        this.projectKey = params['projectKey'];
    });
  }

  ngOnInit() {}

  public addField(id: number) {
    this.fields.push({id: id});
  }

  public removeField(fieldId: number, fieldType: string) {
    let index = this.fields.indexOf(fieldId);
    this.fields.splice(index, 1);
    if (this.projectFieldsCollector) {
      this.removeFieldFromCollector(fieldId, this.convertFieldTypeToField(fieldType));
    }
  }

  public determineField(formData: any) {
    if (formData.fieldType === this.fieldTypes.inputField) {
      this.projectFieldsCollector = new ProjectFieldsCollector();
      this.projectFieldsCollector.inputFieldDtos.push(this.createInputField(formData));
    }
  }
  
  public removeFieldFromCollector(fieldId: number, fieldType: string) {
    this.projectFieldsCollector[fieldType].filter(field => field.id != fieldId);
    console.log(this.projectFieldsCollector);
  }

  private createInputField(formData: any): InputFieldDto {
    return new InputFieldDto(this.fields.length, formData.fieldType, formData.fieldName, formData.isRequired, formData.maxChars, formData.minChars);
  }

  private convertFieldTypeToField(fieldType: string): string {
    if (fieldType === this.fieldTypes.inputField) {
      return 'inputFieldDtos';
    }
  }
}
