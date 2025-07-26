package com.example.qvestdigital.controllers;

import com.example.qvestdigital.dto.CourseDto;
import com.example.qvestdigital.entities.Course;
import com.example.qvestdigital.services.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v2")
public class CourseController {
    private CourseService courseService;
    private ModelMapper modelMapper;

    @Autowired
    public CourseController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "courses")
    public List<CourseDto> getAllCourses(){
        return courseService.getAllCourses().stream().map(this::convertToDto).toList();
    }

    @GetMapping(path = "courses/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable long id){
        Optional<CourseDto> course = convertToDto(courseService.getCourse(id));
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "courses/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable long id, @RequestBody CourseDto courseDto) {
        CourseDto updatedCourseDto = convertToDto( courseService.updateCourse(id, convertToEntity(courseDto)) );
        return new ResponseEntity<CourseDto>(updatedCourseDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "courses")
    public ResponseEntity<Void> createCourse(@RequestBody CourseDto courseDto) {
        Course course = convertToEntity(courseDto);
        course.setId(0);
        Course createdCourse = courseService.createCourse(course);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCourse.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    private CourseDto convertToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    private Optional<CourseDto> convertToDto(Optional<Course> course) {
        return course.map(this::convertToDto);
    }

    private Course convertToEntity(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }
}