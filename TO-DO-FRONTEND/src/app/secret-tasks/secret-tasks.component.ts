import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-secret-tasks',
  templateUrl: './secret-tasks.component.html',
  styleUrl: './secret-tasks.component.css'
})
export class SecretTasksComponent {

  ngOnInit(): void {
    this.getAllTasks();
    this.getSharedNewId();
    this.postNewTask();
  }

  constructor(private _dialog: MatDialog, private _route: Router, private _snackbar: MatSnackBar, private _service: LoginService) {

  }

  allTasks: any = [];

  CurrentFileMessage() {
    this._snackbar.open("You are Currently Viewing Your Primary Tasks!", "Current-File", { duration: 3000 })
  }

  getAllTasks() {
    this._service.getAllSecretTasks().subscribe({
      next: (response: any) => {

        this.tasks = response;
        // alert(this.tasks);

        this.pageSlice = this.tasks.slice(0, 4);
        console.log(this.tasks);
      }
      ,
      error: (err: any) => {
        this._snackbar.open("Issue in Fetching Tasks from the server!", "Failed", { duration: 4000 })
      }
    })
  }

  tasks: any = [];

  public pageSlice = this.tasks.slice(0, 4);

  OnPageChange(event: PageEvent) {
    console.log(event);
    const startIndex = event.pageIndex * event.pageSize;
    let endIndex = startIndex + event.pageSize;
    this.pageSlice = this.tasks.slice(startIndex, endIndex);
  }

  logout() {
    localStorage.removeItem("token");
    this._route.navigate(['home']);
    this._snackbar.open("Logged Out Successfully!", "Success", { duration: 3000 });

  }


  deletemeplease(todoId: any) {
    if (confirm("Are you sure you want to delete this task") == true) {
      this._service.deleteSecretTask(todoId).subscribe({
        next: (res: any) => {
          this._snackbar.open("Deleted Sucessfully!", "Success", { duration: 4000 })
          this.getAllTasks();
        },
        error: (err: any) => {
          this._snackbar.open("Server Issue in Deleting the Task!", "Failed", { duration: 4000 });
        }
      })
    }
  }

  getSharedNewId() {
    let id: string = this._service.shareNewId();
    console.log("ID RECEIVED IS : " + id);
    return id;
  }

  postNewTask() {
    let myId = this.getSharedNewId();
    this._service.TransferTaskToSecretList(myId).subscribe({
      next: (res) => {
        console.log(res)
      },
      error: (err) => {
        console.log("error in archieve task posting")
      }
    })
  }

  ShareThisDataToOtherComponent(todoId: string) {

    if (confirm("Are you sure you want this task in your primary task?") == true) {
      this._service.storeNewId(todoId);
      this._service.transferBackFromSecretTask(todoId).subscribe({
        next: (res) => {
          this._snackbar.open("Task Un-Archived Successfully!", "Success", { duration: 4000 });
          this.getAllTasks(); // Refresh the dashboard tasks after archiving
        },
        error: (err) => {
          this._snackbar.open("Error archiving the task!", "Failed", { duration: 4000 });
        }
      });
    }
  }

  openprimarytask() {
    if (confirm("Are you sure you want to signout from secret task?") == true) {
      this._route.navigate(['dashboard']);
    }
  }

  TakeMeToDashBoard()
  {
    if (confirm("Moving to the Primary Tasks will log you out from Secret Tasks are you sure you want to move?") == true) {
      this._route.navigate(['dashboard']);
    }
  }

}
