import { Component, TemplateRef, OnInit, Input } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {IssueService} from './issue.service';

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
  isLoading: boolean = false;
  projectMembers: Array<String> = [];
  selectedItems = [];
  settings = {};

  constructor(private _modalService: BsModalService, private _issueService: IssueService) {}

  ngOnInit() {
    this.getAssignees();
    console.log(this.projectMembers);

    this.settings = {
      singleSelection: false,
      text: "Select assignees",
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      enableSearchFilter: true,
      badgeShowLimit: 10,
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
    return this._issueService.getAssignees('testUse')
      .subscribe(data => this.projectMembers = data);
  }
}