import { Component, OnInit } from '@angular/core';
import { AddNewTaskComponent } from '../add-new-task/add-new-task.component';
import { MatDialog } from '@angular/material/dialog';



@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrl: './button.component.css'
})
export class ButtonComponent implements OnInit {

  constructor(private _dialog : MatDialog){}

  ngOnInit(): void {
   
  }

  openAddNewTaskForm(){
    this._dialog.open(AddNewTaskComponent);
  }


}
