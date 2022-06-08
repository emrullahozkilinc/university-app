package com.example.universityapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "exams")
public class Exams {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "course_id")
    Long courseId;

    @Column(name = "exam_id")
    int examId;

    @Column(name = "score")
    int score;

    @Override
    public String toString() {
        return "Exams{" +
                "id=" + id +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", examId=" + examId +
                ", score=" + score +
                '}';
    }
}