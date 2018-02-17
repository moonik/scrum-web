import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from "@angular/http";
import {ModalModule} from "ng2-modal"
import { HttpClient } from "./shared/http.client.service";

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
import {ApplicationConstants} from './shared/applicatins-contants';
import { IssueComponent } from './issue/issue.component';
import { FooterComponent } from './footer/footer.component';


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
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule
  ],
  providers: [AuthGuard, AuthenticationService, HttpClient, ProjectService, ApplicationConstants],
  bootstrap: [AppComponent]
})
export class AppModule { }
