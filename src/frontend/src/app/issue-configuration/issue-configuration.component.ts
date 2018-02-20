import { Component, OnInit } from '@angular/core';
import { ProjectFieldsCollector } from '../model/project-fields/ProjectFieldsCollector';
import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { CheckBoxContainerDto } from '../model/project-fields/CheckBoxContainerDto';
import { InputFieldDto } from '../model/project-fields/InputFieldDto';
import { ListElementsContainerDto } from '../model/project-fields/ListElementsContainerDto';
import { RadioButtonContainerDto } from '../model/project-fields/RadioButtonContainerDto';
import { TextAreaDto } from '../model/project-fields/TextAreaDto';
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
  public projectFieldsCollector: ProjectFieldsCollector = new ProjectFieldsCollector();

  constructor() {}

  ngOnInit() {}

  public addField(id: number) {
    this.fields.push({id: id});
  }

  public collectField(field: ProjectFieldDto) {
    if (field.fieldType === this.fieldTypes.inputField) {
      this.projectFieldsCollector.inputFieldDtos.push(field as InputFieldDto);
    }
  }

  private createInputField(inputField: InputFieldDto) {

  }
}
