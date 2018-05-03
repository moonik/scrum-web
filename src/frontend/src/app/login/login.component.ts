import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../security/authentication.service';
import {Router} from '@angular/router';
import {UserDto} from '../model/UserDto';
import {NotificationService} from '../shared/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loading = false;
  error = '';
  userDto: UserDto = new UserDto();
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private authenticationService: AuthenticationService, private router: Router, private ws: NotificationService) {
    this.loginForm = fb.group({
      login : [null,  Validators.required],
      password : [null, [Validators.minLength(8), Validators.required]]
    });
  }

  ngOnInit() {
  }

  init() {
    this.ws.initWebSocketConnection();
  }

  login() {
    if (this.loginForm.valid) {
      this.loading = true;
      this.authenticationService.login(this.userDto)
        .subscribe(
          success => {
            this.router.navigate(['/home']);
          },
          error => {
            if (error.status === 401) {
              this.error = 'Username or password is incorrect';
              this.loading = false;
            }
          });
    } else {
      this.error = 'Username or password incorrect';
    }
  }
}

