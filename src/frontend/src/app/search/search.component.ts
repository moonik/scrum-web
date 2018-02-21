import { Component, OnInit } from '@angular/core';
import {ProjectDto} from "../model/projectDto";
import {SearchService} from "./search.service";
import {ActivatedRoute, Params, Router} from '@angular/router';
import {IssueDto} from "../model/IssueDto";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  providers: [SearchService],
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  projects: ProjectDto[] = [];
  issues: IssueDto[] = [];

  results: string;


  constructor(private searchService: SearchService, private _activeRout: ActivatedRoute, private _router: Router) {
    this._activeRout.params.subscribe((params: Params) => {
      this.searchProjects(params['query']);
      this.searchIssues(params['query']);
    });
  }

  ngOnInit() {

  }

  searchProjects(query: any){
    this.results = query;
    this.searchService.searchProjects(query)
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }

  searchIssues(query: any){
    this.results = query;
    this.searchService.searchIssues(query)
      .subscribe(
        data => {
          this.issues = data;
        }
      );
  }

  goToProjectDetails(projectKey: string) {
    this._router.navigate(['project/details/'+projectKey]);
  }

}
