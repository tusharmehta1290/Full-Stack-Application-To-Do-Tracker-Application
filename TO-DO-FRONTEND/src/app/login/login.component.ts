import { Component, OnInit } from '@angular/core';
import { user } from '../models/user';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent implements OnInit 
{

  existingUser : user = {};
  check: boolean = false;

  email : any = "";
  pss : any = "";

  constructor(private _loginservice : LoginService, private _route : Router, private _snackbar : MatSnackBar) { }

  ngOnInit(): void {
    this.isUserLoggedIn();
  }

  onSubmit(loginform : any){
    this.email = loginform.value.eId;
    this.existingUser.emailId = this.email;

    this.pss = loginform.value.psswd;
    this.existingUser.password = this.pss;

    console.log(loginform);
    console.log(this.existingUser);

    this._loginservice.generateToken(this.existingUser).subscribe({
      next : (res) => {
        this._loginservice.loginUser(res);
        this._route.navigate(['dashboard']);
        this._snackbar.open("User Signed-in Successfully!", "Success", {duration : 3000});
      },
      error : (err) => {
        console.log(err);
        this._snackbar.open("Wrong Credentails",'Error', {duration: 4000});
      }
    })

  }

  isUserLoggedIn()
  {
    this.check =this._loginservice.isloggedIn();  
    if(this.check == true)
    {
      this._route.navigate(['dashboard']);
    }
  }



}
