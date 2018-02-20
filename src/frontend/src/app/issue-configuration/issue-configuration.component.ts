import { Component, OnInit } from '@angular/core';
import { ProjectFieldsCollector } from '../model/project-fields/ProjectFieldsCollector';
import { FieldType } from '../shared/field-type';
import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { CheckBoxContainerDto } from '../model/project-fields/CheckBoxContainerDto';
import { InputFieldDto } from '../model/project-fields/InputFieldDto';
import { ListElementsContainerDto } from '../model/project-fields/ListElementsContainerDto';
import { RadioButtonContainerDto } from '../model/project-fields/RadioButtonContainerDto';
import { TextAreaDto } from '../model/project-fields/TextAreaDto';

@Component({
  selector: 'app-issue-configuration',
  templateUrl: './issue-configuration.component.html',
  styleUrls: ['./issue-configuration.component.css'],
  providers: [FieldType]
})
export class IssueConfigurationComponent implements OnInit {

  fields = [];
  projectFieldsCollector: ProjectFieldsCollector = new ProjectFieldsCollector();

  constructor(private _fieldTypes: FieldType) {}

  ngOnInit() {}

  collectFields() {

  }

  public onAddButton(fieldType: string, fieldName: string) {
    this.fields.push({fieldType: fieldType, fieldname: fieldName});
  }

  public onFieldCreation(field: ProjectFieldDto) {
    if (field.fieldType === this._fieldTypes.inputField) {
      this.projectFieldsCollector.inputFieldDtos.push(field as InputFieldDto);
    }
  }

  private createInputField(inputField: InputFieldDto) {

  }
}
