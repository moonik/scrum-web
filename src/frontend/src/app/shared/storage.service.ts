import { Injectable } from '@angular/core';
import { NotificationService } from './notification.service';

@Injectable()
export class StorageService {

  scope: any | boolean = false;
  notifications = [];
  amountOfNewNotifications = 0;

  constructor(private ws: NotificationService) {
  }

  getScope(): any | boolean {
    return this.scope;
  }

  setScope(scope: any): void {
    this.scope = scope;
  }

  addNotification(notification: any) {
    this.notifications.push(notification);
  }

  subscribeOnNotifications() {
    this.ws.subscribeOnNotifications().subscribe(
      data => {
        this.notifications.unshift(data);
        this.amountOfNewNotifications++;
      }
    );
  }

  getAllNotifications() {
    this.ws.getNotifications().subscribe(data => {
      this.notifications = this.notifications.concat(data);
      this.amountOfNewNotifications = this.notifications.filter(n => !n.seen).length;
    });
  }
}
