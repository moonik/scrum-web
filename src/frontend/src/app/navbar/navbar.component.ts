import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../security/authentication.service';
import {Router} from '@angular/router';
import {SearchService} from '../search/search.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  providers: [SearchService],
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  public searchIcon = '../assets/images/searchicon.png';

  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  public ngOnInit() {
  }

  public search(query: string) {
    if (query !== '') {
      this.router.navigate(['/search/' + query]);
    }
  }

  public checkLogin(): boolean {
    return localStorage.getItem('currentUser') ? true : false;
  }

  public logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

  public getCurrentUser(): string {
    return localStorage.getItem('currentUser');
  }
}
