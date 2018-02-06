import {RouterModule, Routes} from "@angular/router";

import {NgModule} from "@angular/core";
import {LoginComponent} from './login/login.component';
import {RegistrationComponent} from './registration/registration.component';
import {AuthGuard} from './security/auth.service';
import {HomeComponent} from './home/home.component';
import {ProjectComponent} from "./project/project.component";

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegistrationComponent
  },
  {
    path: 'project',
    component: ProjectComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '',
    redirectTo: '/',
    pathMatch: 'full'
  }
];

@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
})

export class AppRoutingModule {}
