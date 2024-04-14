import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { Observable, Subject, empty } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _http : HttpClient) {}
  
  URL : string = "http://localhost:9000";

  currentId: string = "";

  loginUser(token : string){
    localStorage.setItem("token",token);
    return true;
  }

  isloggedIn(){
    let token = localStorage.getItem("token");
    if(token == undefined || token == null || token == ""){
      return false;
    }
    else{
      return true;
    }
  }


  logout(){
    return localStorage.removeItem("token");
  }

  getToken(){
    return localStorage.getItem("token");
  }

  getHeaderObject()
  {
    let token = this.getToken();

    let header_object = new HttpHeaders().set("Authorization", "Bearer "+token)

    return header_object;
  }

  // -------------------------------BACKEND ENDPOINTS CONNECTIONS------------------------------------------

  // User Login to generate token
  generateToken(credentials : any){
    return this._http.post(`${this.URL}/api/v2/login`, credentials, {responseType : 'text'});
  }

  // Registering a new user
  registeringUser(credentials : any){
    return this._http.post(`${this.URL}/api/v1/register`, credentials, {responseType : 'text'});
  }

  // Posting a new task for a specific user
  postingNewTask(newTask: any)
  {
    let httpHead = this.getHeaderObject();

    return this._http.post(`${this.URL}/api/v1/user/todo`, newTask, {headers: httpHead});
  }

  // getting all tasks
  getListOfExistingTasks()
  {
    let httpHead = this.getHeaderObject();
    return this._http.get(`${this.URL}/api/v1/user/todos`, {headers : httpHead});
  }
  

  giveBackEmailFromToken(): Observable<{emailId: string}>
  {
    let httpHead = this.getHeaderObject();
    return this._http.get<{emailId: string}>(`${this.URL}/api/v1/user/getOnlyEmail`,{headers:httpHead});
  }

  //delete a task by id
  deleteTaskById(myId:any)
  {
    let httpHead = this.getHeaderObject();
    return this._http.delete(`${this.URL}/api/v1/user/todo/${myId}`, {headers: httpHead});
  }

  //get username 
  gettingTheEnteredFullName(): Observable<{userName: string}>
  {
    let httpHead = this.getHeaderObject();
    return this._http.get<{userName: string}>(`${this.URL}/api/v1/user/getUsername`, {headers: httpHead});
  }

  // updating any task
  updateExistingTask(task:any)
  {
    let httpHead = this.getHeaderObject();
    return this._http.put(`${this.URL}/api/v1/user/todo`, task, {headers:httpHead});
  }

  // getting a single task by id
  retriveSingletask(myId:string)
  {
    let httpHead = this.getHeaderObject();
    return this._http.get(`${this.URL}/api/v1/user/get-todo/${myId}`,{headers:httpHead});
  }

  // secret task fetching
  getAllSecretTasks()
  {
    let httpHead = this.getHeaderObject();
    return this._http.get(`${this.URL}/api/v1/user/archievedTodoList`, {headers: httpHead})
  }

  // posting new task to secret task
  TransferTaskToSecretList(taskId:string)
  {
    let httpHead = this.getHeaderObject();
    return this._http.post(`${this.URL}/api/v1/user/archieveTodo/${taskId}`, this.retriveSingletask(taskId), {headers:httpHead});
  }

  // deleting a secret task
  deleteSecretTask(myId:string)
  {
    let httpHead = this.getHeaderObject();
    return this._http.delete(`${this.URL}/api/v1/user/archievedTodoList/${myId}`,{headers:httpHead});
  }

  // TRANSFERING SECRET TASK TO MAIN TASKS SECTION
  transferBackFromSecretTask(myId: string)
  {
    let httpHead = this.getHeaderObject();
    return this._http.post(`${this.URL}/api/v1/user/transfer/${myId}`, this.getSingleSecretTask(myId), {headers: httpHead});
  }

  // GETTING SINGLE TASK FOR PASSING POST METHOD OF TRANSFERBACK
  getSingleSecretTask(myId: string)
  {
    let httpHead = this.getHeaderObject();
    return this._http.get(`${this.URL}/api/v1/user/get-stask/${myId}`,{headers: httpHead});
  }

  getSecretCodeOfUser(): Observable<{ secretPassword: string }> {
    let httpHead = this.getHeaderObject();
    return this._http.get<{ secretPassword: string }>(`${this.URL}/api/v1/user/getmesecretpassword`, { headers: httpHead });
  }
  
  // ---------- ADMIN USE ------------------
  getAllAvailableUsersFromAdmin()
  {
    return this._http.get(`${this.URL}/api/v2/admin/getAllUsers`);
  }

  deleteUserByAdminControl(emailId:string)
  {
    return this._http.delete(`${this.URL}/api/v2/admin/deleteUser/${emailId}`);
  }

//---------------------------------- COMPONENTS COMMUNICATIONS OF SIBLINGS ---------------------------------------------------------
  storeNewId(id:string)
  {
    this.currentId = id;
  }

  shareNewId()
  {
    return this.currentId;
  }


// ---------------------------------------- Triggering Event --------------------------------------------------

private taskAddedSource = new Subject<void>();

taskAdded$ = this.taskAddedSource.asObservable();

triggerTaskAdded() {
  this.taskAddedSource.next();
}

priority: string="";

whatPriority(value:string)
{
  this.priority = value;
}

}
