package com.example.universityapp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseDto implements Serializable {
    private final String courseName;
    private final int meanScore;
    private final String scoreLetter;
}
