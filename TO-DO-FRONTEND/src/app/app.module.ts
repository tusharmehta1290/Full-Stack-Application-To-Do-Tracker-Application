import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { LandingpageComponent } from './landingpage/landingpage.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { SignupComponent } from './signup/signup.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSelectModule } from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from './button/button.component';
import { AddNewTaskComponent } from './add-new-task/add-new-task.component';
import { HttpClientModule } from '@angular/common/http';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { EditNewTaskComponent } from './edit-new-task/edit-new-task.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { SecretTasksComponent } from './secret-tasks/secret-tasks.component';
import { AdminCodeComponent } from './admin-code/admin-code.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import {MatTableModule} from '@angular/material/table';
import { SecretCodeComponent } from './secret-code/secret-code.component';
import {MatChipsModule} from '@angular/material/chips';
import { MychartComponent } from './mychart/mychart.component';
import { MatPaginator} from '@angular/material/paginator';
import { PrioritywisetaskComponent } from './prioritywisetask/prioritywisetask.component';




@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    FooterComponent,
    HeaderComponent,
    LandingpageComponent,
    LoginComponent,
    PageNotFoundComponent,
    SignupComponent,
    ButtonComponent,
    AddNewTaskComponent,
    EditNewTaskComponent,
    SecretTasksComponent,
    AdminCodeComponent,
    AdminPanelComponent,
    SecretCodeComponent,
    MychartComponent,
    PrioritywisetaskComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatCheckboxModule,
    MatSelectModule,
    MatPaginatorModule,
    FormsModule,
    HttpClientModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatChipsModule,
    MatPaginator
  ],
  providers: [MatPaginatorModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
