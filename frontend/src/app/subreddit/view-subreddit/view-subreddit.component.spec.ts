import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSubredditComponent } from './view-subreddit.component';

describe('ViewSubredditComponent', () => {
  let component: ViewSubredditComponent;
  let fixture: ComponentFixture<ViewSubredditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewSubredditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewSubredditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
