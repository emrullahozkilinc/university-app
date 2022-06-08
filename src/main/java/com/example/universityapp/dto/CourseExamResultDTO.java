package com.example.universityapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseExamResultDTO {
    String userName;
    int avgScore;
    String scoreLetter;
}
