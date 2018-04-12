import { Component, TemplateRef, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { IssueService } from './issue.service';
import { IssueDetailsDto } from '../model/IssueDetailsDto';
import { UserProfileDto } from '../model/UserProfileDto';
import { IssueDto } from '../model/IssueDto';
import { IssueConfigurationService } from '../issue-configuration/issue-configuration.service';
import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { FieldContentCreatorImpl } from '../model/fields-content/FieldContentCreatorImpl';

import fieldType, * as fieldTypes from '../constants/field-type';

@Component({
  selector: 'app-issue-creation',
  templateUrl: './issue.component.html',
  styleUrls: ['./issue.component.css'],
  providers: [IssueService, IssueConfigurationService, FieldContentCreatorImpl]
})
export class IssueComponent implements OnInit {

  modalRef: BsModalRef;
  config = {
    animated: true,
    keyboard: true,
    backdrop: true,
    ignoreBackdropClick: false
  };

  @Input() projectKey: string;
  @Output() issueCreate = new EventEmitter<IssueDto>();
  issueDetails: IssueDetailsDto = new IssueDetailsDto();
  projectMembers: Array<any> = [];
  selectedItems = [];
  settings = {};
  types: Array<string>;
  issueFields: Array<ProjectFieldDto>;
  fieldTypes = fieldTypes.default;

  constructor(
    private modalService: BsModalService,
    private issueService: IssueService,
    private issueConfService: IssueConfigurationService,
    private fieldCreator: FieldContentCreatorImpl) {
    }

  ngOnInit() {
    this.settings = {
      singleSelection: false,
      text: 'Select assignees',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      enableSearchFilter: true,
      badgeShowLimit: 3,
      classes: 'custom-class-example'
    };
    this.issueConfService.getIssueTypes(this.projectKey).subscribe(
      data => {
        this.types = data;
      }
    );
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template, this.config);
  }

  openModalWithClass(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(
      template,
      Object.assign({}, this.config, { class: 'gray modal-lg' })
    );
  }

  getAssignees() {
    if (this.projectMembers.length === 0) {
      this.issueService.getAssignees(this.projectKey)
        .subscribe(data => this.projectMembers = data);
    }
  }

  createIssue() {
    this.issueDetails.assignees = this.selectedItems.map(item => new UserProfileDto(item.itemName));
    this.issueDetails.fieldsContentCollector = this.getFieldsContent();
    return this.issueService.createIssue(this.projectKey, this.issueDetails)
      .subscribe( data => {
        this.issueCreate.emit(data);
        this.issueDetails = new IssueDetailsDto();
      });
  }

  isValid() {
    return this.issueDetails.summary && this.issueDetails.priority && this.issueDetails.issueType;
  }

  onTypeChange(issueType: string) {
    this.issueService.getIssueFields(issueType, this.projectKey).subscribe(
      data => {
        this.issueFields = data;
      }
    );
  }

  showFields(issueType: string) {
    return issueType && this.issueFields;
  }

  getFieldsContent() {
    this.issueFields.map(f => this.fieldCreator.createField(f));
    console.log(this.fieldCreator.fieldContentCollector);
    return this.fieldCreator.fieldContentCollector;
  }

  ifInputField(fieldType: string) {
    return fieldType === this.fieldTypes.inputField;
  }

  ifCheckBox(fieldType: string) {
    return fieldType === this.fieldTypes.checkBox;
  }

  ifTextArea(fieldType: string) {
    return fieldType === this.fieldTypes.textArea;
  }

  ifList(fieldType: string) {
    return fieldType === this.fieldTypes.list;
  }

  ifRadioButton(fieldType: string) {
    return fieldType === this.fieldTypes.radioButton;
  }
}
