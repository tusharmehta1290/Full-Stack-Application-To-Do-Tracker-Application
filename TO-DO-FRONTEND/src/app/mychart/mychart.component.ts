import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

Chart.register(...registerables);
Chart.register(ChartDataLabels);

@Component({
  selector: 'app-mychart',
  templateUrl: './mychart.component.html',
  styleUrls: ['./mychart.component.css'] 
})
export class MychartComponent implements OnInit {
  totalTasks: any = [];
  myData: any = [];
  myData1:any = [];
  secretTasks: any = [];

  ngOnInit(): void {
    this.NumberOfSecretTasks();
    this.totalNumberOfTasks(); 
  }

  constructor(private _service: LoginService, private _route : Router) {}

  totalNumberOfTasks() {
    this._service.getListOfExistingTasks().subscribe({
      next: (res) => {
        console.log(res);
        this.totalTasks = res;

        // total number of tasks
        const totalNumberOfTasks = this.totalTasks.length;
        this.myData.push(totalNumberOfTasks);

        // number of pending tasks
        const numberOfPendingTasks = this.totalTasks.filter((task: { isCompleted: string; }) => task.isCompleted === 'No').length;
        this.myData.push(numberOfPendingTasks);

        // number of completed tasks
        const numberOfCompletedTasks = this.totalTasks.filter((task: { isCompleted: string; }) => task.isCompleted === 'Yes').length;
        this.myData.push(numberOfCompletedTasks);

        this.myData = this.myData.concat(this.myData1);

        this.RenderChart(); // Render chart after all data is computed

      }
    });
  }

  NumberOfSecretTasks() {
    this._service.getAllSecretTasks().subscribe({
      next: (res) => {
        console.log(res);
        this.secretTasks = res;

        // total number of secret tasks
        const numberOfSecretTasks = this.secretTasks.length;
        this.myData1.push(numberOfSecretTasks);

        // number of completed secret tasks
        const numberOfCompletedSecretTasks = this.secretTasks.filter((task: { isCompleted: string; }) => task.isCompleted === 'Yes').length;
        this.myData1.push(numberOfCompletedSecretTasks);

        // number of not completed secret tasks
        const numberOfNotCompletedSecretTasks = this.secretTasks.filter((task: { isCompleted: string; }) => task.isCompleted === 'No').length;
        this.myData1.push(numberOfNotCompletedSecretTasks);

      }
    });
  }

  RenderChart() {
    const mychart = new Chart("piechart", {
      type: 'bar',
      data: {
        labels: ['Total Number of Primary Tasks', 'Pending Primary Tasks', 'Completed Primary Tasks', 'Total Number of Secret Tasks', 'Completed Secret Tasks', 'Not Completed Secret Tasks'],
        datasets: [{
          label: '# of Tasks',
          data: this.myData,
          backgroundColor: [
            'rgba(37, 152, 236, 0.31)', 
          'rgba(236, 37, 37, 0.36)', 
          'rgba(51, 242, 40, 0.22)', 
          'rgba(37, 152, 236, 0.31)', 
          'rgba(236, 37, 37, 0.36)', 
          'rgba(51, 242, 40, 0.22)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        plugins: {
          datalabels: {
            color : 'black',
            font: {
              size: 14 
            }
          }
        },
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  navigatetodashboard(){
    this._route.navigate(['/dashboard']);

  }

}
