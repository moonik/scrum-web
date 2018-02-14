import { Component, TemplateRef, OnInit } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

@Component({
  selector: 'app-issue-creation',
  templateUrl: './issue.component.html',
  styleUrls: ['./issue.component.css']
})
export class IssueComponent implements OnInit {

  modalRef: BsModalRef;
  config = {
    animated: true,
    keyboard: true,
    backdrop: true,
    ignoreBackdropClick: false
  };
  
  itemList = [];
  selectedItems = [];
  settings = {};

  constructor(private modalService: BsModalService) {}

  ngOnInit() {

    this.itemList = [
      { "id": 1, "itemName": "India" },
      { "id": 2, "itemName": "Singapore" },
      { "id": 3, "itemName": "Australia" },
      { "id": 4, "itemName": "Canada" },
      { "id": 5, "itemName": "South Korea" },
      { "id": 6, "itemName": "Brazil" }
    ];

    this.selectedItems = [
      { "id": 1, "itemName": "India" },
      { "id": 2, "itemName": "Singapore" },
      { "id": 3, "itemName": "Australia" },
      { "id": 4, "itemName": "Canada" }];

      this.settings = {
        singleSelection: false,
        text: "Select Countries",
        selectAllText: 'Select All',
        unSelectAllText: 'UnSelect All',
        enableSearchFilter: true,
        badgeShowLimit: 3,
        classes: "myclass custom-class-example"
      };
  }
  onItemSelect(item: any) {
    console.log(item);
    console.log(this.selectedItems);
  }
  OnItemDeSelect(item: any) {
    console.log(item);
    console.log(this.selectedItems);
  }
  onSelectAll(items: any) {
    console.log(items);
  }
  onDeSelectAll(items: any) {
    console.log(items);
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
}