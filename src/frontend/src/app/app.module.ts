import { BrowserModule} from '@angular/platform-browser';
import { NgModule} from '@angular/core';
import { HttpModule} from '@angular/http';
import { HttpClient} from './shared/http.client.service';
import { AppComponent} from './app.component';
import { LoginComponent} from './login/login.component';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule} from './app-routing.module';
import { RegistrationComponent} from './registration/registration.component';
import { AuthGuard} from './security/auth.guard';
import { AuthenticationService} from './security/authentication.service';
import { HomeComponent} from './home/home.component';
import { NavbarComponent} from './navbar/navbar.component';
import { ProjectComponent} from './project/project.component';
import { ProjectService} from './project/project.service';
import { SearchPipe} from './shared/search.pipe';
import { ProjectConfigurationComponent} from './project-configuration/project-configuration.component';
import { ProjectConfigurationService} from './project-configuration/project-configuration.service';
import { StorageService} from './shared/storage.service';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AngularMultiSelectModule } from 'angular2-multiselect-dropdown/angular2-multiselect-dropdown';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { IssueComponent } from './issue/issue.component';
import { FooterComponent } from './footer/footer.component';
import { IssueFieldsConfigurationComponent } from './issue-configuration/issue-fields-configuration.component';
import { IssueTypeConfigurationComponent } from './issue-configuration/issue-type-configuration.component';
import { IssueConfigurationComponent } from './issue-configuration/issue-configuration.component';
import { SearchComponent } from './search/search.component';
import { LoaderComponent} from './loader/loader.component';
import { LoaderService} from './loader/loader.service';
import { WarningModalComponent } from './warning-modal/warning-modal.component';

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
    SearchPipe,
    ProjectDetailsComponent,
    IssueComponent,
    FooterComponent,
    SearchComponent,
    LoaderComponent,
    WarningModalComponent,
    IssueFieldsConfigurationComponent,
    IssueTypeConfigurationComponent,
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
  providers: [
    AuthGuard,
    AuthenticationService,
    HttpClient,
    ProjectService,
    ProjectConfigurationService,
    StorageService,
    LoaderService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
