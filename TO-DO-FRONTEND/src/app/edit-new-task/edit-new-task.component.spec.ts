import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditNewTaskComponent } from './edit-new-task.component';

describe('EditNewTaskComponent', () => {
  let component: EditNewTaskComponent;
  let fixture: ComponentFixture<EditNewTaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditNewTaskComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditNewTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
