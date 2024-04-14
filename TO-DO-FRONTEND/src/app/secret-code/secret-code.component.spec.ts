import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecretCodeComponent } from './secret-code.component';

describe('SecretCodeComponent', () => {
  let component: SecretCodeComponent;
  let fixture: ComponentFixture<SecretCodeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SecretCodeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SecretCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
