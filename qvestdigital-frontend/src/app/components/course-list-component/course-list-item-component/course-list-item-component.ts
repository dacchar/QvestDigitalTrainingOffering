import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Course } from '../../../models/model';
import { CourseService } from '../../../services/course-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-course-list-item-component',
  imports: [],
  templateUrl: './course-list-item-component.html',
  styleUrl: './course-list-item-component.css'
})
export class CourseListItemComponent {

  @Input() course!: Course
  @Output() deletedCourseEvent = new EventEmitter<number>();
  
  private router = inject(Router);
  private courseService = inject(CourseService);

  updateCourse(id: number): void {
    this.router.navigate(['courses', id]);
  }


  deleteCourse(id: number): void {
    this.courseService.deleteCourse(id).subscribe(response => {
        this.deletedCourseEvent.emit(response);
    })
  }

}
