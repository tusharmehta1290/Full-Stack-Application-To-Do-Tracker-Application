import { Component, OnInit } from '@angular/core';
import { DialogRef } from '@angular/cdk/dialog';
import { NgForm } from '@angular/forms';
import { task } from '../models/Task';
import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-add-new-task',
  templateUrl: './add-new-task.component.html',
  styleUrl: './add-new-task.component.css'
})
export class AddNewTaskComponent implements OnInit {

  constructor(private _services: LoginService,private _snackbar: MatSnackBar,private _dialogClose : DialogRef<AddNewTaskComponent>){}

  ngOnInit(): void {
    
  }

  newTask : task = {};

  minDate = new Date();

  closeform(){
    this._dialogClose.close();
  }

  onSubmit(newTaskForm : any)
  {

    this.newTask.isCompleted = "No";

    if(this.newTask.taskName != null)
    {
      this._services.postingNewTask(this.newTask).subscribe({
        next: (res)=>{
          console.log(res);
          this._dialogClose.close();
          this._snackbar.open("Task Saved Successfully!","Success",{duration: 4000});
          this._services.triggerTaskAdded();
        },
        error: (err)=>{
          this._snackbar.open("Error in saving new task!", "Error",{duration : 4000})
        }
      })
    }  
    newTaskForm.reset();

  }



}
