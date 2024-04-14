import { Component, OnInit } from '@angular/core';
import { LoginService } from './services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'ToDoTrackerApplication';

  constructor(public _loginservice : LoginService, private _route : Router) {}

  ngOnInit(): void {
    
  }

  isAdminRoute(): boolean {
    // Assuming your admin routes are '/admin-code' and '/admin-panel'
    const currentUrl = this._route.url;
    return currentUrl.includes('/admincode') || currentUrl.includes('/adminpanel');
  }
  


}
