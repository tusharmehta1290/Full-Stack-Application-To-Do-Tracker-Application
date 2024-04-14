import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AdminGuardService implements CanActivate {

  permissionCode: string = "";
  logIn: boolean | undefined;

  constructor(private _route: Router, private _snackbar:MatSnackBar) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    if(this.permissionCode === "TODO123")
    {
      this.logIn = true;
      this._snackbar.open("Welcome Admin!", "SignIn Successful",{duration: 4000})
      return this.logIn;
    }

    this._route.navigate(['/admincode']);
    this._snackbar.open("Wrong Code Entered!","failed",{duration:3000})
    this.logIn = false;
    return this.logIn;
  }

}