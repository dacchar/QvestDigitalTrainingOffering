import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Course } from '../../models/model';
import { CourseService } from '../../services/course-service';
import { CourseListItemComponent } from "./course-list-item-component/course-list-item-component";

@Component({
  selector: 'app-course-list-component',
  imports: [ CourseListItemComponent],
  templateUrl: './course-list-component.html',
  styleUrl: './course-list-component.css'
})
export class CourseListComponent implements OnInit {

  private courseService = inject(CourseService);
  private router = inject(Router);

  courses: Course[] = [];

  ngOnInit(): void {
    this.retrieveCourses()
  }

  addCourse(): void {
    this.router.navigate(['courses/add']);
  }

  retrieveCourses() : void {
    this.courseService.retrieveCourses().subscribe(
      response => {
        this.courses = response;
      }
    )
  }

  onDeletedCourse() : void {
    this.retrieveCourses();
  }

}
