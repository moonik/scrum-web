import {Injectable} from '@angular/core';
import {CanActivate, Router, RouterLinkActive} from '@angular/router';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private authenticationService: AuthenticationService) {

  }

  canActivate() {
    if (localStorage.getItem('currentUser')){
      this.authenticationService.refresh(localStorage.getItem('token'), localStorage.getItem('currentUser')).subscribe();
      return true;
    }else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
