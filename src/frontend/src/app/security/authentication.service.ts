import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {UserDto} from '../model/UserDto';
import {HttpClient} from '../shared/http.client.service';
@Injectable()
export class AuthenticationService {

  token: string;

  constructor(private httpClientService: HttpClient) {
    const currentUser = JSON.parse(localStorage.getItem('username'));
    this.token = currentUser && currentUser.token;
  }

  login(userDto: UserDto): Observable<any> {
        return this.httpClientService.post('auth', userDto)
      .map(response => {
        const token = response.json() && response.json().token;
        if (token) {
          this.token = token;
          localStorage.setItem('token', token);
          localStorage.setItem('currentUser', userDto.username);
        }
        return response.status;
      });
  }

  logout(): void {
    this.token = null;
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
  }

  save(userDto: UserDto): Observable<number> {
      return this.httpClientService.post('user-account/save', userDto)
      .map(
        response =>  {
          // console.info('test');
          return response.status;
    // if(response.status ==)
      });
  }

  refresh(token: string, username: string): Observable<any> {
    const tokenObj = {'token': token, 'username': username};
    return this.httpClientService.post('refresh', tokenObj)
      .map(
        response =>  {
          const token = response.json() && response.json().token;
          if (token) {
            this.token = token;
            localStorage.setItem('token', token);
          }
          return response.status;
        });
  }
}


