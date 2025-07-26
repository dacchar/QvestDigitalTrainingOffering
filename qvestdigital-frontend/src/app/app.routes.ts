import { Routes } from '@angular/router';
import { CourseListComponent } from './components/course-list-component/course-list-component';
import { CourseComponent } from './components/course-component/course-component';

export const routes: Routes = [
    { path:'', component: CourseListComponent},
    { path:'courses', component: CourseListComponent  },
    { path:'courses/add', component: CourseComponent  },
    { path:'courses/:id', component: CourseComponent },
];
