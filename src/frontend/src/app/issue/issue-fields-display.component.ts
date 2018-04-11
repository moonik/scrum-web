import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import fieldType, * as fieldTypes from '../constants/field-type';
import { FieldsContentCollector } from '../model/fields-content/FieldsContentCollector';
import { FieldContentCreatorImpl } from '../model/fields-content/FieldContentCreatorImpl';

@Component({
    selector: 'app-issue-fields',
    templateUrl: './issue-fields-display.component.html',
    providers: [FieldContentCreatorImpl]
  })
  export class IssueFieldsDisplay implements OnInit {

    @Input() 
    fields: Array<ProjectFieldDto>;
    @Output() issueCreate = new EventEmitter<string>();
    fieldContentCollector: FieldsContentCollector = new FieldsContentCollector();
    fieldTypes = fieldTypes.default;
    message = '';

    constructor(fieldCreator: FieldContentCreatorImpl) {}

    ngOnInit() {}

    ifInputField(fieldType: string) {
      return fieldType === this.fieldTypes.inputField;
    }

    ifCheckBox(field: ProjectFieldDto) {
      return field.fieldType === this.fieldTypes.checkBox;
    }

    ifTextArea(field: ProjectFieldDto) {
      return field.fieldType === this.fieldTypes.textArea;
    }

    ifList(field: ProjectFieldDto) {
      return field.fieldType === this.fieldTypes.list;
    }

    ifRadioButton(field: ProjectFieldDto) {
      return field.fieldType === this.fieldTypes.radioButton;
    }

    filterOutEmptyContent() {
      this.fields.filter(f => f.content === '' && !f.content);
    }
  }