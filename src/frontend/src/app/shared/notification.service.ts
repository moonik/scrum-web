import {Injectable} from '@angular/core';
import {AlertService} from '../alert/alert.service';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as constants from '../constants/application-constants';

@Injectable()
export class NotificationService {

    private stompClient;

    constructor(private alertService: AlertService) {}

    initWebSocketConnection() {
        let ws = new SockJS(constants.default.API_URL);
        let user = {'user' : localStorage.getItem('currentUser')};
        this.stompClient = Stomp.over(ws);
        let that = this;
        this.stompClient.connect(user, (frame) => {
        that.stompClient.subscribe('/user/queue/reply', (message) => {
            if(message.body) {
                this.alertService.info(message.body);
                console.log(message.body);
            }
        });
        });
    }

    sendNotification(message: any) {
        this.stompClient.send('/app/notification/send', {
        }, JSON.stringify(message));
    }
}