import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../security/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loading = false;
  error = '';
  username: string;
  password: string;
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private authenticationService: AuthenticationService,private router: Router) {
    this.loginForm = fb.group({
      login : [null,  Validators.required],
      password : [null, [Validators.minLength(8), Validators.required]]
    });
  }

  ngOnInit() {
  }

  login() {
    if (this.loginForm.valid) {
      console.info('logged');
      this.loading = true;
      this.authenticationService.login (this.username, this.password)
        .subscribe(
          success => {
            this.router.navigate(['/home']);
          },
          error => {
            if(error.status === 401){
              this.error = 'Username or password is incorrect';
              this.loading = false;
            }
          });
    }else{
      this.error = 'Login or password incorrect';
    }
  }

}
