import {Injectable} from '@angular/core';
import {AlertService} from '../alert/alert.service';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';
import { HttpClient } from './http.client.service';
import { StompService } from 'ng2-stomp-service';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import * as constants from '../constants/application-constants';

@Injectable()
export class NotificationService {

    private stompClient;
    status = null;
    private subject = new Subject<Object>();
    private resolveDisConPromise: (...args: any[]) => void;
    private disconnectPromise: any;

    constructor(private alertService: AlertService, private http: HttpClient) {
        this.disconnectPromise = new Promise(
            (resolve, reject) => this.resolveDisConPromise = resolve
          );
    }

    initWebSocketConnection() {
        const ws = new SockJS(constants.default.API_URL);
        const user = {'user' : localStorage.getItem('currentUser')};
        this.stompClient = Stomp.over(ws);
        this.stompClient.connect(user, this.onSuccess, this.onError);
    }

    onSuccess = (frame: any) => {
        this.status = 'connected';
        this.stompClient.subscribe('/user/queue/reply', (message) => {
            if (message.body) {
                this.alertService.info(message.body);
                this.subject.next(<Object>{ content: message.body, seen: false });
            }
        });
    }

    onError = (error: string) => {
        console.log(error);
        console.log('Reconnecting...');
        setTimeout(() => {
            this.initWebSocketConnection();
        }, 5000);
    }

    unsubscribe() {
	    this.stompClient.unsubscribe();
    }

    disconnect(): Promise<{}> {
        this.status = null;
		this.stompClient.disconnect(() => this.resolveDisConPromise());
		return this.disconnectPromise;
	}

    sendNotification(receiver: string, content: string) {
        const notification = {receiver: receiver, content: content};
        this.stompClient.send('/app/notification/send', {}, JSON.stringify(notification));
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
