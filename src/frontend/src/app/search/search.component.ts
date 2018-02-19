import { Component, OnInit } from '@angular/core';
import {ProjectDto} from "../model/projectDto";
import {SearchService} from "./search.service";
import {ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  providers: [SearchService],
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  projects: ProjectDto[] = [];
  results: string;

  constructor(private searchService: SearchService, private _activeRout: ActivatedRoute) {
    this._activeRout.params.subscribe((params: Params) => {
      this.search(params['query']);
    });
  }

  ngOnInit() {

  }

  search(query: any){
    this.results = query;
    this.searchService.search(query)
      .subscribe(
        data => {
          this.projects = data;
        }
      );
  }


}
