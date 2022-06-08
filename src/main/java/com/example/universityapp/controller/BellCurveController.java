package com.example.universityapp.controller;

import com.example.universityapp.entity.Course;
import com.example.universityapp.entity.User;
import com.example.universityapp.pojo.UserCourseScore;
import com.example.universityapp.repo.CourseRepository;
import com.example.universityapp.repo.ExamsRepository;
import com.example.universityapp.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "7. Soru için yazılan REST API")
@RestController
@RequestMapping("/bellcurve")
public class BellCurveController {

    CourseRepository courseRepository;
    UserRepository userRepository;
    ExamsRepository examsRepository;

    public BellCurveController(CourseRepository courseRepository, UserRepository userRepository, ExamsRepository examsRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.examsRepository = examsRepository;
    }

    @ApiOperation(value = "Çan eğrisi hesaplama işlemi")
    @GetMapping("/getUserScore/{courseId}")
    ResponseEntity<UserCourseScore> getUsersScoreWithBellCurve(@PathVariable Long courseId) {
        Course course = courseRepository.findById(courseId).get();
        List<User> users = course.getUsers();

        return new ResponseEntity<>(examsRepository.findExamsAvgScore(), HttpStatus.OK);

    }
}
