package com.example.universityapp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCourseScore {
    Long userId;
    Long courseId;
    Double score;
}
