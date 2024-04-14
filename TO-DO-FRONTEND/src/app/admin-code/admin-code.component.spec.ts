import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCodeComponent } from './admin-code.component';

describe('AdminCodeComponent', () => {
  let component: AdminCodeComponent;
  let fixture: ComponentFixture<AdminCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminCodeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
