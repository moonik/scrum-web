import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueConfigurationComponent } from './issue-configuration.component';

describe('IssueConfigurationComponent', () => {
  let component: IssueConfigurationComponent;
  let fixture: ComponentFixture<IssueConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IssueConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
