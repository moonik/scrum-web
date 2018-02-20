import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from "@angular/http";
import { HttpClient } from "./shared/http.client.service";
import { ModalModule } from 'ngx-bootstrap/modal';
import { AngularMultiSelectModule } from 'angular2-multiselect-dropdown/angular2-multiselect-dropdown';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { RegistrationComponent } from './registration/registration.component';
import { AuthGuard } from './security/auth.guard';
import { AuthenticationService } from './security/authentication.service';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ProjectComponent } from './project/project.component';
import {ProjectService} from "./project/project.service";
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { IssueComponent } from './issue/issue.component';
import { FooterComponent } from './footer/footer.component';
import { IssueConfigurationComponent } from './issue-configuration/issue-configuration.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HomeComponent,
    NavbarComponent,
    ProjectComponent,
    ProjectDetailsComponent,
    IssueComponent,
    FooterComponent,
    IssueConfigurationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule.forRoot(),
    AngularMultiSelectModule
  ],
  providers: [AuthGuard, AuthenticationService, HttpClient, ProjectService],
  bootstrap: [AppComponent]
})
export class AppModule { }
