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

<<<<<<< HEAD
  error = '';
  userDto: UserDto = new UserDto();
  loginForm: FormGroup;
=======
  public loading = false;
  public error = '';
  public userDto: UserDto = new UserDto();
  public loginForm: FormGroup;
>>>>>>> ce78f7396326a017d91c8a064934c2d5820f5502

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

  public login() {
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
