import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { task } from '../models/Task';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import {MatPaginatorModule} from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { EditNewTaskComponent } from '../edit-new-task/edit-new-task.component';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  ngOnInit(): void {
    this.getAllTasks();
    this.getMyFullNameAndGreet();

    this.TakeMeToAdmin();

    this._service.taskAdded$.subscribe(() => {
      this.getAllTasks();
      this.isThereAnyTasks = true;
    });
  }

  constructor(private _dialog : MatDialog, private _route : Router, private _snackbar : MatSnackBar, private _service:LoginService, private cdr : ChangeDetectorRef) {

  }

  allTasks: any = [];
  selectedCategory : string | null=null;
  filteredtask : any = [];
  searchterm : string = "";
  showFilterChipBar : boolean = true;

  isThereAnyTasks:boolean=true;

  myEmail:any={
    emailId: ""
  }

  myuserName: string = "";

  CurrentFilePrompt()
  {
    this._snackbar.open("You are Currently Viewing Your Primary Tasks!", "Current-File", {duration: 3000})
  }

  checkNumberoftasks() {
  this.isThereAnyTasks = false;
}


  getAllTasks()
  {
    this._service.getListOfExistingTasks().subscribe({
      next: (response)=>
      {
        
        this.tasks = response;
        console.log(this.tasks)
        this.pageSlice = this.tasks.slice(0,4);
        console.log(this.tasks);
      }
      ,
      error:(err)=>{
        if (err.status === 500) {
          this.checkNumberoftasks();
        }
      }
    });
  }

  tasks: any = [];

  public pageSlice = this.tasks.slice(0, 4);

  OnPageChange(event: PageEvent) {
    console.log(event);
    const startIndex = event.pageIndex * event.pageSize;
    const endIndex = startIndex + event.pageSize;
    this.pageSlice = this.tasks.slice(startIndex, endIndex);
}

  openEditTaskForm(id: string) {
    const dialogRef = this._dialog.open(EditNewTaskComponent, {
      data: { todoId: id }
    });
  
    // Subscribe to the afterClosed() event to handle the result when the dialog is closed
    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog closed with result:', result);
    });
  }
  

  logout(){
    localStorage.removeItem("token");
    this._route.navigate(['home']);
    this._snackbar.open("Logged Out Successfully!","Success", {duration : 3000});
  }

  deletemeplease(todoId:any)
  {
    if(confirm("Are you sure you want to delete this task")==true)
    {
      this._service.deleteTaskById(todoId).subscribe({
        next: (res)=>{
          this._snackbar.open("Deleted Sucessfully!", "Success",{duration: 4000})
          this.getAllTasks()
        },
        error: (err) =>{
          this._snackbar.open("Server Issue in Deleting the Task!", "Failed", {duration:4000});
        }
      })
    }
  }
  
  ShareThisDataToOtherComponent(todoId: string) 
  {
  
    if(confirm("Are you sure that you want to move this task in your SECRET SECTION?")==true)
    {
      this._service.storeNewId(todoId);
      this._service.TransferTaskToSecretList(todoId).subscribe({
        next: (res) => {
          this._snackbar.open("Task Archived Successfully!", "Success", { duration: 4000 });
          this.getAllTasks(); // Refresh the dashboard tasks after archiving
        },
        error: (err) => {
          this._snackbar.open("Error archiving the task!", "Failed", { duration: 4000 });
        }
      });
    
    }
  
  }

  TakeMeToAdmin()
  {
    this._service.giveBackEmailFromToken().subscribe({
      next: (res)=>{
        console.log(res);
        this.myEmail.emailId = res.emailId;
      },
      error: (err)=>{
        console.log(err);
      }
    })
  }

  TakeMeToAdminNavigation()
  {
    this._route.navigate(['adminpanel']);
    this._snackbar.open("Navigated Admin Successfully!","Success",{duration: 4000})
  }

  filtertaskbypriority(priority : string | null){
    console.log("$$$");
    this.selectedCategory = priority;
    this.filtertask();

    this.updatePageSlice();

    this.showFilterChipBar = priority === null;
    
  }

  filtertask() : void{
    console.log("Filtering tasks");
    console.log("Selected category :", this.selectedCategory);
    console.log("All tasks :", this.tasks);

    this.filteredtask = this.tasks.filter((item: { priority: any, taskName : any; }) => {
      const categoryMatch = this.selectedCategory === null || item.priority === this.selectedCategory;
      const searchtermmatch = this.searchterm ? item.taskName.toLowerCase().includes(this.searchterm.toLowerCase())
      :true;
      return categoryMatch && searchtermmatch;
    })

    console.log("Filtered tasks :", this.filteredtask);
    this.updatePageSlice();
  }

  clearSearch(){
    this.searchterm = "";
    this.filtertask();
  }

  updatePageSlice(){
    this.pageSlice = this.filteredtask.slice(0,4);
  }

  navigatetochart(){
    this._route.navigate(['/chart']);
  }

  openprioritywisetask(value:string){
    this._service.whatPriority(value);
    this._route.navigate(['/prioritywisetask']);
  }

  getMyFullNameAndGreet() {
    this._service.gettingTheEnteredFullName().subscribe({
      next: (res) => {
        if (typeof res === 'string') {
          console.log("My Name is : " + res);
        } else {
          console.log("My Name is : " + res.userName);
        }
      },
      error: (err) => {
        this.myuserName = err.error.text;
      }
    });
  }
  


}

