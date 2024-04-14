import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminGuardService } from '../services/admin.guard';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-admin-code',
  templateUrl: './admin-code.component.html',
  styleUrl: './admin-code.component.css'
})
export class AdminCodeComponent implements OnInit
{

  ngOnInit(): void {
    this.isThisDirectAdmin()
  }
  
  myEmail: any ={
    emailId:""
  }

  constructor(private _route : Router, private _guard: AdminGuardService, private _snackbar:MatSnackBar, private _service: LoginService){}

  admincode : string = "";
  adminCodeInvalid : boolean = false;

  validateAdminCode()
  {
    this._guard.permissionCode = this.admincode;
    this._route.navigate(['adminpanel']);
  }

  isThisDirectAdmin()
  {
    this._service.giveBackEmailFromToken().subscribe({
      next: (res)=>{
        console.log(res);
        this.myEmail.emailId = res.emailId;

        if(this.myEmail.emailId == "tusharmehta1290@gmail.com" || this.myEmail.emailId == "manavwaje9967@gmail.com")
        {
          this.admincode = "TODO123";
          this.validateAdminCode();
        }

      },
      error:(err)=>{
        console.log(err);
      }
    })
  }
  

}
