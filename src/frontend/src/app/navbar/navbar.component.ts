import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../security/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
  }

  checkLogin(): boolean{
    return localStorage.getItem('currentUser') ? true : false;
  }

  logout(){
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

  navigate(path: string){
    this.router.navigate([path]);
  }
}
