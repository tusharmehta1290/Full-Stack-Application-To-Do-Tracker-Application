import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingpageComponent } from './landingpage/landingpage.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { SignupComponent } from './signup/signup.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SecretTasksComponent } from './secret-tasks/secret-tasks.component';
import { AdminCodeComponent } from './admin-code/admin-code.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { SecretCodeComponent } from './secret-code/secret-code.component';
import { AdminGuardService } from './services/admin.guard';
import { SecretTaskService } from './services/secret-task.service';
import { MychartComponent } from './mychart/mychart.component';
import { PrioritywisetaskComponent } from './prioritywisetask/prioritywisetask.component';
import { AuthGuardService } from './services/auth-guard.service';

const routes: Routes = [
  { path : "home", component : LandingpageComponent},
  { path : "", redirectTo : "/home", pathMatch : "full"},
  { path : "login", component : LoginComponent },
  { path : "secretcode", component : SecretCodeComponent},
  { path : "secret-tasks", component: SecretTasksComponent, canActivate: [SecretTaskService] },
  { path : "signup", component : SignupComponent},
  { path : "dashboard", component : DashboardComponent, canActivate : [AuthGuardService]},
  { path : "admincode", component : AdminCodeComponent},
  { path : "adminpanel", component : AdminPanelComponent, canActivate : [AdminGuardService]},
  { path : "chart", component:MychartComponent },
  { path : "prioritywisetask", component : PrioritywisetaskComponent},
  { path : "**", component : PageNotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
