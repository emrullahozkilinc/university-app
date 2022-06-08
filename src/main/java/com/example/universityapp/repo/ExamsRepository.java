package com.example.universityapp.repo;

import com.example.universityapp.entity.Exams;
import com.example.universityapp.pojo.UserCourseScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamsRepository extends JpaRepository<Exams, Long> {
    Exams findExamByCourseIdAndUserIdAndExamId(Long courseId, Long userId, int examId);

    List<Exams> findExamsByUserIdAndCourseId(Long userId, Long courseId);

    List<Exams> findExamsByExamId(int examId);

    @Query(value = "select new com.example.universityapp.pojo.UserCourseScore(e.userId,e.courseId,avg(e.score)) from Exams e group by e.userId,e.courseId")
    UserCourseScore findExamsAvgScore();
}