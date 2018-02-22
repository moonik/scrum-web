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

  public projects: ProjectDto[] = [];
  public issues: IssueDto[] = [];

  public results: string;

  constructor(private searchService: SearchService, private _activeRoute: ActivatedRoute, private _router: Router) {
    this._activeRoute.params.subscribe((params: Params) => {
      this.searchProjects(params['query']);
      this.searchIssues(params['query']);
    });
  }

  ngOnInit() {

  }

  public searchProjects(query: any){
    this.results = query;
    this.searchService.searchProjects(query)
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }

  public searchIssues(query: any){
    this.results = query;
    this.searchService.searchIssues(query)
      .subscribe(
        data => {
          this.issues = data;
        }
      );
  }

  public goToProjectDetails(projectKey: string) {
    this._router.navigate(['project/details/'+projectKey]);
  }

}
