package com.example.qvestdigital.CourseJpaRepository;

import com.example.qvestdigital.entities.Course;
import com.example.qvestdigital.repositories.CourseJpaRepository;
import com.example.qvestdigital.services.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseJpaRepository courseJpaRepository;

    @Test
    @DisplayName("it should save the course")
    void saveCourse() {
        Course course = Course.builder()
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();

        when(courseJpaRepository.save(any(Course.class))).thenReturn(course);

        Course savedCourse = courseJpaRepository.save(course);
        assertNotNull(savedCourse);
        assertEquals("Java", savedCourse.getName());
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

        when(courseJpaRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<Course> courses = courseService.getAllCourses();

        assertNotNull(courses);
        assertEquals(2, courses.size());
    }

    @Test
    @DisplayName("it should return course by id.")
    void getCourseById() {
        Course course = Course.builder()
                .id(1)
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();

        when(courseJpaRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Optional<Course> existingCourse = courseService.getCourse(1);

        assertNotNull(existingCourse);
        assertEquals(1, existingCourse.get().getId());
    }
}
