import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecretTasksComponent } from './secret-tasks.component';

describe('SecretTasksComponent', () => {
  let component: SecretTasksComponent;
  let fixture: ComponentFixture<SecretTasksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SecretTasksComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SecretTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
