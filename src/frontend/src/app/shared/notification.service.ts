import {Injectable} from '@angular/core';
import {AlertService} from '../alert/alert.service';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';
import { HttpClient } from './http.client.service';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as constants from '../constants/application-constants';

@Injectable()
export class NotificationService {

    private stompClient;
    private subject = new Subject<Object>();

    constructor(private alertService: AlertService, private http: HttpClient) {}

    initWebSocketConnection() {
        let ws = new SockJS(constants.default.API_URL);
        let user = {'user' : localStorage.getItem('currentUser')};
        this.stompClient = Stomp.over(ws);
        let that = this;
        this.stompClient.connect(user, (frame) => {
            that.stompClient.subscribe('/user/queue/reply', (message) => {
                if(message.body) {
                    this.alertService.info(message.body);
                    this.subject.next(<Object>{ content: message.body, seen: false });
                    console.log(message.body);
                }
            });
        });
    }

    sendNotification(receiver: string, content: string) {
        let notification = {receiver: receiver, content: content};
        this.stompClient.send('/app/notification/send', {
        }, JSON.stringify(notification));
    }

    subscribeOnNotifications(): Observable<any> {
        return this.subject.asObservable();
    }

    getNotifications() {
        return this.http.get('user-account/notifications').map(res => res.json());
    }

  updateNotifications() {
      return this.http.put('user-account/update-notifications', null);
  }    
}