import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpModule} from "@angular/http";
import {HttpClient} from "./shared/http.client.service";
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {RegistrationComponent} from './registration/registration.component';
import {AuthGuard} from './security/auth.service';
import {AuthenticationService} from './security/authentication.service';
import {HomeComponent} from './home/home.component';
import {NavbarComponent} from './navbar/navbar.component';
import {ProjectComponent} from './project/project.component';
import {ProjectService} from "./project/project.service";
import {FooterComponent} from './footer/footer.component';
import {SearchPipe} from "./shared/search.pipe";
import {ProjectConfigurationComponent} from "./projectConfiguration/project-configuration.component";
import {ProjectConfigurationService} from "./projectConfiguration/project-configuration.service";
import {StorageService} from "./shared/storage.service";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    HomeComponent,
    NavbarComponent,
    ProjectComponent,
    FooterComponent,
    ProjectConfigurationComponent,
    SearchPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [AuthGuard,
    AuthenticationService,
    HttpClient,
    ProjectService,
    ProjectConfigurationService,
    StorageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
