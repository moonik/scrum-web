import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
  import 'rxjs/add/operator/map';
import {UserDto} from '../model/userDto';
@Injectable()
export class AuthenticationService {

  public token: string;
  headers: Headers = new Headers();

  constructor(private http: Http) {

    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.token = currentUser && currentUser.token;
    this.headers.append('Authorization', '');
    this.headers.append('Content-Type', 'application/json');
  }

  login(username: string, password: string): Observable<any> {
      return this.http.post(`/api/scrum-web/auth`, JSON.stringify({username: username, password: password}), {headers: this.headers})
      .map(response => {
        let token = response.json() && response.json().token;
        if (token) {
          this.token = token;
          localStorage.setItem('currentUser', JSON.stringify({username: username, token: token}));
        }
        return response.status;
      });
  }
  logout(): void {
    this.token = null;
    localStorage.removeItem('currentUser');
  }

  save(userDto: UserDto): Observable<number>{
    return this.http.post('http://localhost:8080/save',JSON.stringify(userDto),{headers: this.headers})
      .map(
        response =>  {
          // console.info('test');
          return response.status;
    // if(response.status ==)
      });
  }
}

