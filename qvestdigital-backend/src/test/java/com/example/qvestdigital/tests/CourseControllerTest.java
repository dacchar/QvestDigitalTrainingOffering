package com.example.qvestdigital.tests;

import com.example.qvestdigital.controllers.CourseController;
import com.example.qvestdigital.dto.CourseDto;
import com.example.qvestdigital.entities.Course;
import com.example.qvestdigital.services.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseController courseController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    @DisplayName("it should get course by id")
    void getCourse() throws Exception {
        long courseId = 12;
        Course course = Course.builder()
                .id(courseId)
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();
        CourseDto responseCourseDto = CourseDto.builder()
                .id(10)
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();


        when(courseService.getCourse(courseId)).thenReturn(Optional.ofNullable(course));
        when(modelMapper.map(eq(course), eq(CourseDto.class))).thenReturn(responseCourseDto);

        MvcResult result = mockMvc.perform(
                get("/api/v2/courses/{id}", courseId))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        CourseDto actualResponseCourseDto = objectMapper.readValue(content, CourseDto.class);

        assertEquals(responseCourseDto, actualResponseCourseDto);
    }

    @Test
    @DisplayName("it should throw 404 if course not found")
    void getCourseNotFound() throws Exception {
        long courseId = 12;

        when(courseService.getCourse(courseId)).thenReturn(Optional.empty());

       mockMvc.perform(get("/api/v2/courses/{id}", courseId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("it should update the course")
    void saveCourse() throws Exception {

        long courseId = 10;
        CourseDto requestCourseDto = CourseDto.builder()
                .id(10)
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();
        Course course = Course.
                builder()
                .id(10)
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();
        CourseDto responseCourseDto = CourseDto.builder()
                .id(10)
                .name("Java")
                .description("Java course")
                .price(180.0)
                .instructor("Max Mustermann")
                .build();

        when(modelMapper.map(eq(requestCourseDto), eq(Course.class))).thenReturn(course);
        when(courseService.updateCourse(eq(courseId), eq(course))).thenReturn(course);
        when(modelMapper.map(eq(course), eq(CourseDto.class))).thenReturn(responseCourseDto);

        var result = mockMvc.perform(put("/api/v2/courses/{id}", courseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestCourseDto)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        var actualResponseCourseDto = objectMapper.readValue(content, CourseDto.class);

        assertEquals(responseCourseDto, actualResponseCourseDto);
    }
}
