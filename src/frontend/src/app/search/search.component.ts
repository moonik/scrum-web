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

  searchResults: SearchResultsDto[] = [];
  searchQuery: string;
  roles = roles.default;
  rolesTypes = Object.values(this.roles);

  constructor(private searchService: SearchService,
              private activeRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.activeRoute.params.subscribe((params: Params) => {
      this.search(params['query']);
    });
  }

  search(query: any) {
    this.searchQuery = query;
    this.searchService.searchResults(query)
      .subscribe(
        data => {
          this.searchResults = data;
        }
      );
  }

  goToProjectDetails(projectKey: string) {
    this.router.navigate(['project/details/' + projectKey]);
  }

  requestAccess(role: string, projectKey: string) {
    const member: ProjectMemberDto = new ProjectMemberDto(localStorage.getItem('currentUser'), role);
    this.searchService.askForAccess(projectKey, member).subscribe(
      () => {
        this.ngOnInit();
      });
  }

  checkMembers(project: ProjectDto): boolean {
    return this.searchService.findUserInAssignees(project.members) || this.searchService.findUserInMembers(project.requests);
  }
}
