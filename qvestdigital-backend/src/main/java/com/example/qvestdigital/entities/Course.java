package com.example.qvestdigital.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@EqualsAndHashCode
public class Course {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private Double price;
    private String instructor;
}
