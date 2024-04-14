import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { AdminGuardService } from '../services/admin.guard';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})

export class AdminPanelComponent implements OnInit {

  constructor(public _loginservice: LoginService, private _snackbar: MatSnackBar, private _route:Router, private _guard:AdminGuardService) { }

  ngOnInit(): void {
    this.getDataFromBackend();
    this.getEmail();
  }

  newObj:any ={
    emailId:""
  }

  currentExistingData: any = [];
  pageSlice: any[] = []; 

  displayedColumns: string[] = ['EmailId', 'Button'];

  OnPageChange(event: PageEvent) {
    const startIndex = event.pageIndex * event.pageSize;
    let endIndex = startIndex + event.pageSize;
    if (endIndex > this.currentExistingData.length) {
      endIndex = this.currentExistingData.length;
    }
    this.pageSlice = this.currentExistingData.slice(startIndex, endIndex);
  }

  getDataFromBackend() {
    this._loginservice.getAllAvailableUsersFromAdmin().subscribe({
      next: (res) => {
        this.currentExistingData = res;
        this.pageSlice = this.currentExistingData.slice(0, 8); 
        console.log("***" + JSON.stringify(this.currentExistingData));
      },
      error: (err) => {
        console.log("Error Occurred");
      }
    })
  }

  DeleteThisUserAdmin(emailId: string) {
    if (confirm("Are you sure you want to delete the registration of the user") == true) {
      this._loginservice.deleteUserByAdminControl(emailId).subscribe({
        next: (res) => {
          this._snackbar.open("User Deleted Successfully!", "Success", { duration: 4000 })
          this.getDataFromBackend();
        },
        error: (err) => {
          this._snackbar.open("Some issue occurred in deleting the user", "Failed", { duration: 4000 })
        }
      })
    }
  }

  SignMeOut()
  {
    this._snackbar.open("Signed Out Successfully!", "Success", {duration:4000});
    this._route.navigate(['admincode']);    
  }

  getEmail()
  {
    this._loginservice.giveBackEmailFromToken().subscribe({
      next: (res)=>{
        this.newObj.emailId = res.emailId;
      },
      error: (err)=>{
        console.log(err);
      }
    })
  }


}
