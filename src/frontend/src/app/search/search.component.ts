import { Component, OnInit } from '@angular/core';
import {ProjectDto} from "../model/projectDto";
import {SearchService} from "./search.service";
import {ActivatedRoute, Params, Router} from '@angular/router';
import {IssueDto} from "../model/IssueDto";
import {SearchResultsDto} from "../model/SearchResultsDto";
import {ProjectMemberDto} from "../model/projectMemberDto";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  providers: [SearchService],
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  public searchresults: SearchResultsDto[] = [];

  public results: string;
  result: any;

  constructor(private searchService: SearchService,
              private _activeRoute: ActivatedRoute,
              private _router: Router) {
    this._activeRoute.params.subscribe((params: Params) => {
      this.searchResults(params['query']);
    });
  }

  ngOnInit() {

  }

  public searchResults(query: any){
    this.results = query;
    this.searchService.searchResults(query)
      .subscribe(
        data => {
          console.log(data);
          this.searchresults = data;
        }
      );
  }

  public goToProjectDetails(projectKey: string) {
    this._router.navigate(['project/details/' + projectKey]);
  }

  requestAccess(key: string) {
    this.searchService.askForAccess(key, localStorage.getItem('currentUser')).subscribe();
  }

  checkMembers(result: ProjectMemberDto[]): boolean {
    return result.map(m => m.username).includes(localStorage.getItem('currentUser'));
  }
}
