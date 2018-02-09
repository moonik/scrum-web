import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../security/authentication.service';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserDto} from '../model/userDto';
import {passwordMatch} from '../shared/password-match.directive';
import {Router, RouteReuseStrategy} from '@angular/router';


function passwordConfirming(c: AbstractControl): any {
  if (!c.parent || !c) { return; }
  const pwd = c.parent.get('password');
  const cpwd = c.parent.get('repassword');

  if (!pwd || !cpwd) { return ; }
  if (pwd.value !== cpwd.value) {
    if (c == pwd) {
      c.parent.get('repassword').setErrors({passwordConfirming: true});
    }
    if (c == cpwd) {
      return {passwordConfirming: true};
    }
  } else {
    c.parent.get('repassword').setErrors(null);
  }
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  userDto: UserDto = new UserDto();
  registrationForm: FormGroup;
  usernameExistError: string;

  constructor(private authenticationService: AuthenticationService, fb: FormBuilder, private router: Router) {

    this.registrationForm = fb.group({
      username : [null, [Validators.required, Validators.minLength(5), Validators.maxLength(9)]],
      firstname : [null, Validators.required],
      lastname : [null, Validators.required],
      // email : [null,  Validators.email],
      password : [null, [Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$'), passwordConfirming]],
      repassword : [null, [Validators.required, passwordConfirming]]
    });

  }

  ngOnInit(): void {
  }

  register() {
    this.authenticationService.save(this.userDto).subscribe(
        success => {
          this.router.navigate(['/login']);
        }, error => {
            if(error.status === 409){
              this.usernameExistError = error._body;
            }
      });
  }

  checkControl(name: string): boolean {
    return this.registrationForm.controls[name].invalid && (this.registrationForm.controls[name].touched || this.registrationForm.controls[name].dirty);
  }

  checkUsernameLength(): boolean {
    return this.registrationForm.controls.username.errors.minlength || this.registrationForm.controls.username.errors.maxlength;
  }

  checkPasswordMatch(): boolean {
    return this.registrationForm.controls.repassword.errors.passwordConfirming && this.registrationForm.controls.repassword.value != null;
  }
}
