import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NgForm } from '@angular/forms';
import { task } from '../models/Task'; // Update the import path based on your actual model
import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-edit-new-task',
  templateUrl: './edit-new-task.component.html',
  styleUrls: ['./edit-new-task.component.css']
})
export class EditNewTaskComponent implements OnInit {
  todoId: string = '';
  editTask: task = {};
  minDate = new Date();

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<EditNewTaskComponent>,
    private _loginService: LoginService,
    private _snackBar : MatSnackBar
  ) {}

  ngOnInit(): void {
    this.todoId = this.data?.todoId || '';
    console.log('Received todoId in EditNewTaskComponent:', this.todoId);

    if (this.todoId) {
      this.getSingleTask();
    }
    
  }

  closeform() {
    this.dialogRef.close();
  }

  onSubmit(edittaskform: NgForm) {
    // Perform save or update logic here
    console.log('Form submitted:', this.editTask);
    edittaskform.reset();
    this.dialogRef.close(); // Close the dialog after submission
  }

  getSingleTask() {
    this._loginService.retriveSingletask(this.todoId).subscribe({
      next: (res) => {
        this.editTask = res;
      },
      error: (err) => {
        console.log("Error in task retrieval");
      }
    });
  }

  SubmitEditTask() {
    this.editTask.todoId = this.todoId;

    this._loginService.updateExistingTask(this.editTask).subscribe({
      next: (res) => {
        this._snackBar.open("Task Edited Successfully", "Success", {duration: 4000})
        this._loginService.triggerTaskAdded();
        console.log(res);
      },
      error: () => {
        this._snackBar.open("Error in saving the Edits, Try Again!", "Error", {duration: 4000})
      }
    });
  }
}
