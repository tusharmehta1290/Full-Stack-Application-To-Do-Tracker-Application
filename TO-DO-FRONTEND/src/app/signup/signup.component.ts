import { Component, OnInit } from '@angular/core';
import { user } from '../models/user';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit {


  newUser: user = {};
  check: boolean = false;

  name: any = "";
  email: any = "";
  secd: any = "";
  passw: any = "";

  isLoading : boolean = false;

  constructor(private _loginservice: LoginService, private _route: Router, private _snackbar : MatSnackBar) { }

  ngOnInit(): void {
    this.isUserLoggedIn();
  }

  onSubmit(signupform: any) {

    this.isLoading = true;

    this.name = signupform.value.uName;
    this.newUser.userName = this.name;

    this.email = signupform.value.eId;
    this.newUser.emailId = this.email;

    this.passw = signupform.value.pswd;
    this.newUser.password = this.passw;
    

    console.log(this.newUser);
    console.log(signupform);

    this._loginservice.registeringUser(this.newUser).subscribe({
      next: (res: any) => {
        this._route.navigate(['login']);
        this._snackbar.open("Registration is Successfull", "Success", {duration : 3000});
        console.log(res);       
      },
      error: (err: any) => {
        console.log("Error while registering");
        if(err.status == 403)
        {
          this._snackbar.open("User Already Present with the email, Try again with other email!",'Error', {duration : 4000})
          this.isLoading = false;
        }
        else
        {
          this._snackbar.open("Internal Server Error in registering the user");
          this.isLoading = false;
        }
        
      },
      complete : () =>{
        this.isLoading = false;
      }
    })

    signupform.resetForm();
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
