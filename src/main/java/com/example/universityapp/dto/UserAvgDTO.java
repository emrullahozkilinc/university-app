package com.example.universityapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserAvgDTO {
    String name;
    double avg;
    String scoreLetter;

    public UserAvgDTO(String name, double avg) {
        this.name = name;
        this.avg = avg;
    }
}
