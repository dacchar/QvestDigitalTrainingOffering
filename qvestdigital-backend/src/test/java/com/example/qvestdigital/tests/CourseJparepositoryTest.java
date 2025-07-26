package com.example.qvestdigital.tests;

import com.example.qvestdigital.entities.Course;
import com.example.qvestdigital.repositories.CourseJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CourseJparepositoryTest {

    @Autowired
    CourseJpaRepository courseJpaRepository;

    @Test
    @DisplayName("it should save the course")
    void saveCourse() {
        Course course = Course.builder()
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();
        Course newCourse = courseJpaRepository.save(course);
        assertNotNull(newCourse);
        assertNotEquals(0, newCourse.getId());
    }

    @Test
    @DisplayName("it should return all courses")
    void getCourses() {
        Course course1 = Course.builder()
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();
        courseJpaRepository.save(course1);

        Course course2 = Course.builder()
                .name("JUNIT")
                .description("JUNI course")
                .price(150.0)
                .instructor("Helmut Schubauer")
                .build();
        courseJpaRepository.save(course2);

        List<Course> courses = courseJpaRepository.findAll();
        assertNotNull(courses);
        assertEquals(2, courses.size());
    }

    @Test
    @DisplayName("it should return course by id.")
    void getCourseById() {
        Course course = Course.builder()
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();
        courseJpaRepository.save(course);

        Course existingCourse = courseJpaRepository.findById(1L).orElseGet(null);
        assertNotNull(existingCourse);
        assertEquals("Java", existingCourse.getName());
    }

    @Test
    @DisplayName("it should update the course")
    void updateCourse() {
        Course course = Course.builder()
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();
        Course savedCourse = courseJpaRepository.save(course);

        Course existingCourse = courseJpaRepository.findById(savedCourse.getId()).orElseGet(null);
        existingCourse.setDescription("The Java course");
        Course updatedCourse = courseJpaRepository.save(existingCourse);
        assertEquals("The Java course", existingCourse.getDescription());
    }

    @Test
    @DisplayName("it should delete the course")
    void deleteCourse() {
        Course course1 = Course.builder()
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();
        courseJpaRepository.save(course1);
        Long id = course1.getId();

        Course course2 = Course.builder()
                .name("JUNIT")
                .description("JUNI course")
                .price(150.0)
                .instructor("Helmut Schubauer")
                .build();
        courseJpaRepository.save(course2);

        courseJpaRepository.deleteById(course1.getId());
        Optional<Course> existingCourse = courseJpaRepository.findById(id);
        List<Course> courses = courseJpaRepository.findAll();
        assertEquals(1, courses.size());
        assertEquals(Optional.empty(), existingCourse);
    }
}
