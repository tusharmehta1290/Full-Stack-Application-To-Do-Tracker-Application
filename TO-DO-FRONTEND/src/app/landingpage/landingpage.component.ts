import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrl: './landingpage.component.css'
})
export class LandingpageComponent implements OnInit 
{
  check: boolean = false;

  constructor(private _service:LoginService,private _route:Router) {}

  ngOnInit(): void {
      this.isUserLoggedIn();
  }

  isUserLoggedIn()
  {
    this.check =this._service.isloggedIn();  
    if(this.check == true)
    {
      this._route.navigate(['dashboard']);
    }
  }

}
