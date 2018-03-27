import {AbstractControl, ValidatorFn} from '@angular/forms';

export function passwordMatch(): ValidatorFn {
 return(control: AbstractControl): {[key: string]: any} => {
   console.log(control.parent);
   if (!control.parent || !control) { return; }
   const pwd = control.parent.get('password');
   const cpwd = control.parent.get('repassword');

   console.log(pwd.value + ' ' + cpwd.value);
   if (!pwd || !cpwd) { return ; }
   if (pwd.value !== cpwd.value) {
     return { invalid: true };
   }
  };
}
