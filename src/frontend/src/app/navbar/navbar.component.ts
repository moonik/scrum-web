import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../security/authentication.service';
import {Router} from '@angular/router';
import {SearchService} from '../search/search.service';
import {NotificationService} from '../shared/notification.service';
import { StorageService } from '../shared/storage.service';

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

  constructor(
    private authenticationService: AuthenticationService, 
    private router: Router, 
    private ws: NotificationService,
    private sotarge: StorageService) {
  }

  ngOnInit() {
    if (localStorage.getItem('currentUser')) {
      if (!this.ws.status) {
        this.ws.initWebSocketConnection();
      }
      this.getNotifications();
      this.ws.subscribeOnNotifications().subscribe(
        data => {
          this.notifications.unshift(data);
          this.amountOfNewNotifications++;
        }
      );
    }
  }

  getNotifications() {
    this.ws.getNotifications().subscribe(data => {
      console.log(data);
      this.notifications = this.notifications.concat(data);
      this.amountOfNewNotifications = this.notifications.filter(n => !n.seen).length;
    })
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
    this.notifications.map(n => n.seen = true);
    this.ws.updateNotifications().subscribe(
      success => this.amountOfNewNotifications = 0
    );
  }
}
