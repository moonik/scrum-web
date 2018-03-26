import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../security/authentication.service';
import {Router} from '@angular/router';
import {UserDto} from '../model/userDto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  error = '';
  userDto: UserDto = new UserDto();
  loginForm: FormGroup;

  constructor(private fb: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router) {
    this.loginForm = fb.group({
      login: [null, Validators.required],
      password: [null, [Validators.minLength(8), Validators.required]]
    });
  }

  ngOnInit() {
  }

  login() {
    if (this.loginForm.valid) {
      console.log('logged');
      this.authenticationService.login(this.userDto)
        .subscribe(
          () => {
            this.router.navigate(['/home']);
          },
          error => {
            if (error.status === 401) {
              this.error = 'Username or password is incorrect';
            }
          });
    } else {
      this.error = 'Username or password incorrect';
    }
  }

}
