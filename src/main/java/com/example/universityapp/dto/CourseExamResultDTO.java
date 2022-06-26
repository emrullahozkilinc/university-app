package com.example.universityapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseExamResultDTO {
    Long userId;
    double avgScore;
    String scoreLetter;
}
