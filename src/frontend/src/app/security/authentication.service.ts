import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
  import 'rxjs/add/operator/map';
import {UserDto} from '../model/userDto';
import {HttpClient} from '../shared/http.client.service';
@Injectable()
export class AuthenticationService {

  public token: string;
  headers: Headers = new Headers();

  constructor(private httpClientService: HttpClient) {

    let currentUser = JSON.parse(localStorage.getItem('username'));
    this.token = currentUser && currentUser.token;
    this.headers.append('Authorization', '');
    this.headers.append('Content-Type', 'application/json');
  }

  login(userDto: UserDto): Observable<any> {
        return this.httpClientService.post(`/api/scrum-web/auth`, userDto)
      .map(response => {
        let token = response.json() && response.json().token;
        if (token) {
          this.token = token;
          localStorage.setItem('token', token);
          localStorage.setItem('currentUser', JSON.stringify({username: userDto.username}));
        }
        return response.status;
      });
  }

  logout(): void {
    this.token = null;
    localStorage.removeItem('currentUser');
  }

  save(userDto: UserDto): Observable<number>{
      return this.httpClientService.post('/api/scrum-web/user-account/save', userDto)
      .map(
        response =>  {
          // console.info('test');
          return response.status;
    // if(response.status ==)
      });
  }
}
