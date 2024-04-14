import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SecretTaskService } from '../services/secret-task.service';

@Component({
  selector: 'app-secret-code',
  templateUrl: './secret-code.component.html',
  styleUrl: './secret-code.component.css'
})
export class SecretCodeComponent implements OnInit {

  constructor(private _route : Router, private _service:LoginService, private _snackbar:MatSnackBar, private _secretcode:SecretTaskService){}
  ngOnInit(): void {
    this.getSecretCodeOfUser();  
  }

  permissioncode : string = "";
  permissionCodeInvalid : boolean = false;

  //checked this is working fine!
  mySecretPassword: any = {
    secretPassword: ""
  };

  validatePermissionCode()
  {

    this._secretcode.getEnteredCode = this.permissioncode;
    this._secretcode.getSecretCode = this.mySecretPassword.secretPassword;

    this._route.navigate(['secret-tasks']);
  }


  getSecretCodeOfUser()
  {
    this._service.getSecretCodeOfUser().subscribe({
      next: (res)=>{
        console.log(res);
       this.mySecretPassword.secretPassword = res.secretPassword;
      },
      error: (err)=>{
        console.log(err);
      }
    })    
  }

}
