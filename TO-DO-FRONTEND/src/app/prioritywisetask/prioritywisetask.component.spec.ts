import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrioritywisetaskComponent } from './prioritywisetask.component';

describe('PrioritywisetaskComponent', () => {
  let component: PrioritywisetaskComponent;
  let fixture: ComponentFixture<PrioritywisetaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrioritywisetaskComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PrioritywisetaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
