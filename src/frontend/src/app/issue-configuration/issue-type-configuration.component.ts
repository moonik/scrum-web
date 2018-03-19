import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-issue-type-configuration',
    templateUrl: './issue-type-configuration.component.html',
    styleUrls: ['./issue-configuration.component.css']
  })
  export class IssueTypeConfigurationComponent {

    public types = [];

    constructor(){}

    public addNewType(type: string) {
      this.types.push({id: null});
    }

  }