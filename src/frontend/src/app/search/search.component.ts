import {Component, OnInit} from '@angular/core';
import {ProjectDto} from '../model/projectDto';
import {SearchService} from './search.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {SearchResultsDto} from '../model/SearchResultsDto';
import {ProjectMemberDto} from '../model/projectMemberDto';
import * as roles from '../constants/roles';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  providers: [SearchService],
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  public searchResults: SearchResultsDto[] = [];
  public searchQuery: string;
  public roles = roles.default;
  public rolesTypes = Object.values(this.roles);

  constructor(private searchService: SearchService,
              private _activeRoute: ActivatedRoute,
              private _router: Router) {
  }

  ngOnInit() {
    this._activeRoute.params.subscribe((params: Params) => {
      this.search(params['query']);
    });
  }

  public search(query: any) {
    this.searchQuery = query;
    this.searchService.searchResults(query)
      .subscribe(
        data => {
          this.searchResults = data;
        }
      );
  }

  public goToProjectDetails(projectKey: string) {
    this._router.navigate(['project/details/' + projectKey]);
  }

  requestAccess(role: string, projectKey: string) {
    const member: ProjectMemberDto = new ProjectMemberDto();
    member.projectKey = projectKey;
    member.username = localStorage.getItem('currentUser');
    member.role = role;
    this.searchService.askForAccess(member).subscribe(
      () => {
        this.ngOnInit();
      });
  }

  checkMembers(project: ProjectDto): boolean {
    return (project.members.map(m => m.username).includes(localStorage.getItem('currentUser')) ||
      project.requests.map(r => r.username).includes(localStorage.getItem('currentUser')));
  }
}
