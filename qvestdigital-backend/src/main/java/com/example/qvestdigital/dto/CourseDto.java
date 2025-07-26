package com.example.qvestdigital.dto;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class CourseDto {
    private long id;
    private String name;
    private String description;
    private Double price;
    private String instructor;
}
