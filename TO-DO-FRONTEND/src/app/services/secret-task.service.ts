import { Injectable, OnInit } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SecretTaskService implements CanActivate, OnInit {

  getEnteredCode : string = "";
  getSecretCode : string = "X";

  constructor(private _snackbar:MatSnackBar,private _login:LoginService, private _route:Router) { }
  
  
  ngOnInit(): void {

  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> 
  {
    
    if(this.getEnteredCode === this.getSecretCode)
    {
      this._snackbar.open("Secret Tasks login success","success",{duration:4000})
      return true;
    }
    this._snackbar.open("Wrong Code", "Failed",{duration:3000})
    this._route.navigate(['secretcode'])
    return false;
  }

}
