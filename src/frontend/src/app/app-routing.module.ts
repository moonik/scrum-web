import {RouterModule, Routes} from "@angular/router";

import {NgModule} from "@angular/core";
import {LoginComponent} from './login/login.component';
import {RegistrationComponent} from './registration/registration.component';
import {AuthGuard} from './security/auth.guard';
import {HomeComponent} from './home/home.component';
import {ProjectComponent} from "./project/project.component"
import {ProjectDetailsComponent} from './project-details/project-details.component';
import {IssueComponent} from './issue/issue.component';
import {IssueConfigurationComponent} from './issue-configuration/issue-configuration.component';

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
    path: 'project/:projectKey/configuration/issues',
    component: IssueConfigurationComponent,
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
