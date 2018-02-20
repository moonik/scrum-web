import { Component, TemplateRef, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { IssueService } from './issue.service';
import { IssueDetailsDto } from '../model/IssueDetailsDto';
import { UserProfileDto } from '../model/UserProfileDto';
import { IssueDto } from '../model/IssueDto';

@Component({
  selector: 'app-issue-creation',
  templateUrl: './issue.component.html',
  styleUrls: ['./issue.component.css'],
  providers: [IssueService]
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
  @Output() onIssueCreate = new EventEmitter<IssueDto>();
  public issueDetails: IssueDetailsDto = new IssueDetailsDto();
  projectMembers: Array<any> = [];
  selectedItems = [];
  settings = {};

  constructor(private _modalService: BsModalService, private _issueService: IssueService) {}

  ngOnInit() {
    this.getAssignees();
    this.settings = {
      singleSelection: false,
      text: "Select assignees",
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      enableSearchFilter: true,
      badgeShowLimit: 3,
      classes: "custom-class-example"
    };
  }
 
  openModal(template: TemplateRef<any>) {
    this.modalRef = this._modalService.show(template, this.config);
  }
 
  openModalWithClass(template: TemplateRef<any>) {
    this.modalRef = this._modalService.show(
      template,
      Object.assign({}, this.config, { class: 'gray modal-lg' })
    );
  }

  getAssignees() {
    return this._issueService.getAssignees(this.projectKey)
      .subscribe(data => this.projectMembers = data);
  }

  createIssue() {
    this.issueDetails.assignees = this.selectedItems.map(item => new UserProfileDto(item.itemName));
    return this._issueService.createIssue(this.projectKey, this.issueDetails)
      .subscribe( data => {
        this.onIssueCreate.emit(data);
        this.issueDetails = new IssueDetailsDto(); 
      });
  }

  isValid() {
    return this.issueDetails.summary && this.issueDetails.priority && this.issueDetails.issueType;
  }
}