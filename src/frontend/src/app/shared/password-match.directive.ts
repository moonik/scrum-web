import {AbstractControl, ValidatorFn} from '@angular/forms';

export function passwordMatch(): ValidatorFn {
 return (control: AbstractControl):{[key: string]: any} => {
   if (!control.parent || !control) return;
   const pwd = control.parent.get('password');
   const cpwd= control.parent.get('repassword')
   if (!pwd || !cpwd) return ;
   if (pwd.value !== cpwd.value) {
     return { invalid: true };
   }
  };
}
