import { Component, inject } from '@angular/core';
import { Course } from '../../models/model';
import { CourseService } from '../../services/course-service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-course-component',
  imports: [FormsModule],
  templateUrl: './course-component.html',
  styleUrl: './course-component.css'
})
export class CourseComponent {
  protected id!: number;
  protected course: Course = new Course(this.id, '', '', '', 0);

  private orderService = inject(CourseService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  
  ngOnInit(): void {
    if(this.route.snapshot.routeConfig!.path !== 'courses/add'){
      this.id = parseInt(this.route.snapshot.params['id'])
      this.orderService.retrieveCourse(this.id).subscribe(
        data => this.course = data
      )
    }
  }

  saveCourse(): void {
    if(this.route.snapshot.routeConfig!.path === 'courses/add'){
      this.orderService.createCourse(this.course).subscribe(
        data => { 
          this.router.navigate(["courses"]);
        }
      )
    } else {
      this.orderService.updateCourse(this.id, this.course).subscribe(
        data => { 
          this.router.navigate(["courses"]);
        }
      )
    }
  }

  cancel(): void {
    this.router.navigate(["courses"]);    
  }

}
