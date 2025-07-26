package com.example.qvestdigital.services;

import com.example.qvestdigital.entities.Course;
import com.example.qvestdigital.repositories.CourseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseJpaRepository courseJpaRepository;

    @Autowired
    public CourseService(CourseJpaRepository courseJpaRepository) {
        this.courseJpaRepository = courseJpaRepository;
    }

    public List<Course> getAllCourses() {
        return courseJpaRepository.findAll();
    }

    public Optional<Course> getCourse(long id) {
        return courseJpaRepository.findById(id);
    }

    public Course updateCourse(long id, Course course) {
        return courseJpaRepository.save(course);
    }

    public void deleteCourse(long id) {
            courseJpaRepository.deleteById(id);
    }

    public Course createCourse(Course course) {
        course.setId(0);
        return courseJpaRepository.save(course);
    }
}
