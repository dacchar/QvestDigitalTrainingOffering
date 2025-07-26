import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseListComponent } from './course-list-component';
import { CourseService } from '../../services/course-service';
import { of } from 'rxjs';
import { Course } from '../../models/model';
import {  DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';


describe('CourseListComponent', () => {
  let component: CourseListComponent;
  let fixture: ComponentFixture<CourseListComponent>;

  let courseServiceSpy: jasmine.SpyObj<CourseService>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [CourseListComponent],
  
      providers: [{
        provide: CourseService,
        useValue: jasmine.createSpyObj('CourseService', ['retrieveCourses']),
      },
    ]
    })
    .compileComponents();
  });

  it('should display loaded courses', () => {

    const courses = [
      new Course(1, "Java", "My Java course", "My Java instructor", 200),
      new Course(2, "Angular", "My Angular course", "My Angular instructor", 150)
    ]

    courseServiceSpy = TestBed.inject(CourseService) as jasmine.SpyObj<CourseService>;
    courseServiceSpy.retrieveCourses.and.returnValue(of(courses));

    fixture = TestBed.createComponent(CourseListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    const courseListItemDebugElement: DebugElement = fixture.debugElement;
    const courseListItems = courseListItemDebugElement.queryAll(By.css('app-course-list-item-component'));
    
    expect(courseListItems.length).toEqual(2);
    
  });

});
