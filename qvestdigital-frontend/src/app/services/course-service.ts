import { inject, Injectable } from '@angular/core';
import { Course } from '../models/model';
import { Observable, of } from 'rxjs';
import { API_URL } from '../app.constants';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private http = inject(HttpClient);

  retrieveCourses(): Observable<Course[]> {
    const path = `${API_URL}/courses`;
    return this.http.get<[Course]>(path);
  }
  
  retrieveCourse(id: number): Observable<Course> {
    let path = `${API_URL}/courses/${id}`
    return this.http.get<Course>(path);
  }

  createCourse(order: Course): Observable<Course> {
    const path = `${API_URL}/courses`;
    return this.http.post<Course>(path, order);
  }

  updateCourse(id: number, todo: Course): Observable<Course> {
    let path = `${API_URL}/courses/${id}`
    return this.http.put<Course>(path, todo);
  }

  deleteCourse(id: number): Observable<number> {
    let path = `${API_URL}/courses/${id}`
    return this.http.delete<number>(path);
  }

}
