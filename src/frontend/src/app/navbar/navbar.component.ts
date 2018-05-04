import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../security/authentication.service';
import {Router} from '@angular/router';
import {SearchService} from '../search/search.service';
import {NotificationService} from '../shared/notification.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  providers: [SearchService],
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  searchIcon = '../assets/images/searchicon.png';
  notifications = [];
  amountOfNewNotifications = 0;

  constructor(private authenticationService: AuthenticationService, private router: Router, private ws: NotificationService) {
  }

  ngOnInit() {
    if (localStorage.getItem('currentUser')) {
      this.ws.initWebSocketConnection();
      this.ws.getNotifications().subscribe(
        data => {
          this.notifications = this.notifications.concat(data);
          this.amountOfNewNotifications = this.notifications.filter(n => !n.seen).length;
      });
      this.ws.subscribeOnNotifications().subscribe(
        data => {
          this.notifications.unshift(data);
          this.amountOfNewNotifications++;
        }
      );
    }
  }

  search(query: string) {
    if (query !== '') {
      this.router.navigate(['/search/' + query]);
    }
  }

  checkLogin(): boolean {
    return localStorage.getItem('currentUser') ? true : false;
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

  getCurrentUser(): string {
    return localStorage.getItem('currentUser');
  }

  updateNotifications() {
    this.amountOfNewNotifications = 0;
    this.notifications.map(n => n.seen = true);
    this.ws.updateNotifications().subscribe(
      success => this.amountOfNewNotifications = 0
    );
  }
}
