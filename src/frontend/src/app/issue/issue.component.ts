import { Component, TemplateRef, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { IssueService } from './issue.service';
import { IssueDetailsDto } from '../model/IssueDetailsDto';
import { UserProfileDto } from '../model/UserProfileDto';
import { IssueDto } from '../model/IssueDto';
import { IssueConfigurationService } from '../issue-configuration/issue-configuration.service';
import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { IssueFieldsDisplay } from './issue-fields-display.component';

@Component({
  selector: 'app-issue-creation',
  templateUrl: './issue.component.html',
  styleUrls: ['./issue.component.css'],
  providers: [IssueService, IssueConfigurationService]
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
  @ViewChild(IssueFieldsDisplay) child;

  constructor(
    private modalService: BsModalService,
    private issueService: IssueService,
    private issueConfService: IssueConfigurationService) {}

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
    return this.issueService.createIssue(this.projectKey, this.issueDetails)
      .subscribe( data => {
        this.issueCreate.emit(data);
        this.issueDetails = new IssueDetailsDto();
      });
  }

  isValid() {
    return this.issueDetails.summary && this.issueDetails.priority && this.issueDetails.issueType;
  }

  onTypeChange() {
    this.issueService.getIssueFields(this.issueDetails.issueType, this.projectKey).subscribe(
      data => {
        this.issueFields = data;
      }
    );
  }

  showFields() {
    return this.issueDetails.issueType && this.issueFields;
  }
}
