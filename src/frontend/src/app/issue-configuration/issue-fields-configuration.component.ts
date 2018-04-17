import { Component, OnInit, Input, OnChanges, HostListener } from '@angular/core';
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
import { ProjectFieldCreatorImpl } from '../model/project-fields/ProjectFieldCreatorImpl';
import { IssueConfigurationService } from './issue-configuration.service';

import * as fieldTypes from '../constants/field-type';
import { ProjectFieldsCollector } from '../model/project-fields/ProjectFieldsCollector';

@Component({
  selector: 'app-issue-fields-configuration',
  templateUrl: './issue-fields-configuration.component.html',
  styleUrls: ['./issue-configuration.component.css'],
  providers: [ProjectFieldCreatorImpl, IssueConfigurationService]
})
export class IssueFieldsConfigurationComponent implements OnInit, OnChanges {
  fields = [];
  oldFields = '';
  fieldTypes = fieldTypes.default;
  fieldTypesArray = Object.values(this.fieldTypes);
  chooseIssueType = 'Choose issue type...';
  chosenIssueType = '';
  selectedField: any;
  @Input()
  issueTypes;
  @Input()
  private projectKey;

  constructor(
    private activatedRoute: ActivatedRoute,
    private fieldCreator: ProjectFieldCreatorImpl,
    private service: IssueConfigurationService) {}

  ngOnInit() {}

  ngOnChanges(changes) {
    this.fetchFields();
  }

  fetchFields() {
    if (this.chosenIssueType && this.chosenIssueType !== this.chooseIssueType) {
      this.service.getProjectFields(this.projectKey, this.chosenIssueType)
      .subscribe(data => { this.fields = data; this.oldFields = JSON.stringify(data); });
    }
  }

  addField($event) {
    this.setSubmit();
    let field = {id: null, submitted: false, elements: []};
    this.selectedField = field;
    this.clickInside($event, this.selectedField);
    this.fields.push(field);
  }

  showAddFieldButton() {
    return this.chosenIssueType !== this.chooseIssueType && this.chosenIssueType !== '';
  }

  addFieldElement(field: any, id: number) {
    field.elements.push({id: id});
  }

  removeField(field: any, fieldType: string) {
    const index = this.fields.indexOf(field);
    this.fields.splice(index, 1);
    if (field.id != null) {
      this.service.removeField(field.id, this.projectKey, this.chosenIssueType)
        .subscribe(data => { this.oldFields = JSON.stringify(data); });
    }
  }

  removeFieldElement(field: any, element: any) {
    const index = this.fields.indexOf(element);
    field.elements.splice(index, 1);
  }

  setSubmit() {
    if (this.selectedField) {
      this.selectedField.submitted = true;
    }
  }

  isValidGeneralData(formData: any) {
    return (formData.fieldType && formData.fieldName) && (formData.fieldType !== '' && formData.fieldName !== '');
  }

  isParamsElements(fieldType: string) {
    return fieldType !== this.fieldTypes.inputField && fieldType !== this.fieldTypes.textArea && fieldType;
  }

  isParamsTextField(fieldType: string) {
    return fieldType === this.fieldTypes.inputField || fieldType === this.fieldTypes.textArea;
  }

  getFieldElement(fieldType: string) {
    if (fieldType === this.fieldTypes.checkBox) {
      return 'checkbox';
    } else if (fieldType === this.fieldTypes.list) {
      return 'list element';
    } else if (fieldType === this.fieldTypes.radioButton) {
      return 'radio button';
    }
  }

  canSubmit() {
    return this.fields.length > 0 && this.compare() && this.checkSumbitted();
  }

  createFields() {
    if (this.filterOut().length > 0) {
      this.service.createFields(this.collectFields(), this.projectKey, this.chosenIssueType)
        .subscribe(data => { this.fields = data; this.oldFields = JSON.stringify(data); });
      this.fieldCreator.projectFieldsCollector = new ProjectFieldsCollector();
    }
  }

  clickInside($event: Event, field: any) {
    $event.preventDefault();
    $event.stopPropagation();  // <- that will stop propagation on lower layers
    this.setSubmit();
    field.submitted = false;
    this.selectedField = field;
  }

  @HostListener('document:click', ['$event']) clickedOutside($event) {
    if (this.selectedField && this.isValidGeneralData(this.selectedField)) {
      this.selectedField.submitted = true;
    } else if (this.selectedField && !this.isValidGeneralData(this.selectedField)) {
      this.removeField(this.selectedField, this.selectedField.fieldType);
      this.selectedField = null;
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
    this.fields.map(field => this.fieldCreator.createField(field));
    return this.fieldCreator.projectFieldsCollector;
  }
}
