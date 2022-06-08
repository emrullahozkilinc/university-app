package com.example.universityapp.repo;

import com.example.universityapp.entity.Course;
import com.example.universityapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
/*
    @Query("select u from User u where u.id = :courseId " +
            "left join UserC c on c.id = ")
    Integer countAverageExamScoreByCourses(Long courseId);

 */
}