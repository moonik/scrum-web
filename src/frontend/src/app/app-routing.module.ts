import {RouterModule, Routes} from '@angular/router';

import {NgModule} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {RegistrationComponent} from './registration/registration.component';
import {AuthGuard} from './security/auth.guard';
import {HomeComponent} from './home/home.component';
import {ProjectComponent} from './project/project.component';
import {ProjectConfigurationComponent} from './project-configuration/project-configuration.component';
import {ProjectDetailsComponent} from './project-details/project-details.component';

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
    path: 'project/details/:projectKey',
    component: ProjectDetailsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '',
    redirectTo: '/',
    pathMatch: 'full'
  },
  {
    path: 'project/configure/:id',
    component: ProjectConfigurationComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
