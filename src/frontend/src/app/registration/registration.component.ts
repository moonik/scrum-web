import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../security/authentication.service';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserDto} from '../model/userDto';
import {passwordMatch} from '../shared/password-match.directive';
import {Router, RouteReuseStrategy} from '@angular/router';


function passwordConfirming(c: AbstractControl): any {
  if(!c.parent || !c) return;
  const pwd = c.parent.get('password');
  const cpwd= c.parent.get('repassword');

  if(!pwd || !cpwd) return ;
  if (pwd.value !== cpwd.value) {
    if(c == pwd) {
      c.parent.get('repassword').setErrors({passwordConfirming: true});
    }
    if(c == cpwd) {
      return {passwordConfirming: true};
    }
  }
  else{
    console.info("match");
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

  constructor(private authenticationService: AuthenticationService, fb: FormBuilder, private router: Router){

    this.registrationForm = fb.group({
      username : [null, [Validators.required, Validators.minLength(5), Validators.maxLength(9)]],
      firstname : [null, Validators.required],
      lastname : [null, Validators.required],
      // email : [null,  Validators.email],
      password : [null, [Validators.required, Validators.minLength(8), Validators.pattern("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"), passwordConfirming]],
      // repassword : [null, [Validators.required]]
      repassword : [null, [Validators.required, passwordConfirming]]
    }, {
      // validator: passwordConfirming
    });

  }

  ngOnInit(): void {
  }

  findInvalidControls() {
    const invalid = [];
    const controls = this.registrationForm.controls;
    for (const name in controls) {
      if (controls[name].invalid) {
        invalid.push(name);
      }
    }
    return invalid;
  }

  register() {
    // console.info('register');
    this.authenticationService.save(this.userDto).subscribe(
        success => {
          this.router.navigate(['/login']);
        });
  }
}
