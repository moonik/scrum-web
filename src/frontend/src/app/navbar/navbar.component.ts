import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../security/authentication.service';
import {Router} from '@angular/router';
import {SearchService} from "../search/search.service";
import {ProjectDto} from "../model/projectDto";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  providers: [SearchService],
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  netImage:any = "../assets/images/searchicon.png";

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
  }

  search(phrase){
    console.log(phrase);
    this.router.navigate(['/search/'+phrase]);
  }

  checkLogin(): boolean{
    return localStorage.getItem('currentUser') ? true : false;
  }

  logout(){
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

  getCurrentUser(): string{
    return localStorage.getItem('currentUser');
  }

}
