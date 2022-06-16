package com.example.universityapp.repo;

import com.example.universityapp.dto.UserAvgDTO;
import com.example.universityapp.entity.Course;
import com.example.universityapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query( name="getUserAvgByCourseId",
            nativeQuery = true)
    List<UserAvgDTO> countAverageExamScoreByCourses(@Param("id") Long courseId);

    @Query( name="getGreaterThen",
            nativeQuery = true)
    List<UserAvgDTO> getUsersGreaterThenScore(@Param("score") Integer score);
}