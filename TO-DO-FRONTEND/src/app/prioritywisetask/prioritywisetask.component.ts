import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-prioritywisetask',
  templateUrl: './prioritywisetask.component.html',
  styleUrls: ['./prioritywisetask.component.css']
})
export class PrioritywisetaskComponent {

  constructor(private _route: Router, private _service: LoginService) { }

  mypriorities: string = "";
  tasks: any = [];
  HighPriTasks: any = [];
  mediumpritasks: any = [];
  lowpritasks: any = [];
  selectedPriority: string = 'High';
  initialized: boolean = false; // Flag to track if tasks are already fetched

  ngOnInit(): void {
    this.mypriorities = this._service.priority;
    if (!this.initialized) {
      this.filterTasksByPriority(this.mypriorities);
    }
  }

  filterTasksByPriority(priority: string) {
    this.selectedPriority = priority;
    if (!this.initialized) {

      this._service.getListOfExistingTasks().subscribe({
        next: (response) => {
          this.tasks = response;
          this.initialized = true; 
          this.filterTasksLocally(priority);
        },
        error: (err) => {
          console.log("Error fetching tasks:", err);
        }
      });
    } else {
      this.filterTasksLocally(priority);
    }
  }

  filterTasksLocally(priority: string) {
    if (priority === 'High') {
      this.HighPriTasks = this.tasks.filter((task: any) => task.priority === "High");
    } else if (priority === 'Medium') {
      this.mediumpritasks = this.tasks.filter((task: any) => task.priority === "Medium");
    } else if (priority === "Low") {
      this.lowpritasks = this.tasks.filter((task: any) => task.priority === "Low");
    }
  }

  navigatetodashboard() {
    this._route.navigate(['/dashboard']);
  }
}
