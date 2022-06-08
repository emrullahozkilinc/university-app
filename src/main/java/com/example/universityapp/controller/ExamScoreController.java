package com.example.universityapp.controller;

import com.example.universityapp.dto.CourseDto;
import com.example.universityapp.entity.Course;
import com.example.universityapp.entity.Exams;
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

import java.util.ArrayList;
import java.util.List;

@Api(value = "5. Soru için yazılan REST API")
@RestController
@RequestMapping("/examScore")
public class ExamScoreController {

    ExamsRepository examsRepository;
    UserRepository userRepository;

    public ExamScoreController(ExamsRepository examsRepository, UserRepository userRepository) {
        this.examsRepository = examsRepository;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Öğrencilerin aldığı derslerin not ortalaması")
    @GetMapping("/getScoreMean/{userId}")
    ResponseEntity<List<CourseDto>> getScoreMean(@PathVariable Long userId) {
        if (userRepository.findById(userId).isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        List<CourseDto> courseDtos = new ArrayList<>();
        for(Course course : userRepository.findById(userId).get().getCourses()) {
            List<Exams> exams = examsRepository.findExamsByUserIdAndCourseId(userId, course.getId());
            int sum = 0;
            if(!exams.isEmpty())
                sum = exams.stream().mapToInt(Exams::getScore).sum();
            courseDtos.add(new CourseDto(course.getCourseName(), sum / 3, getScoreLetter(sum / 3)));
        }

        return ResponseEntity.ok(courseDtos);
    }

    @ApiOperation(value = "Öğrencinin GPA hesabı")
    @GetMapping("/getGPA/{userId}")
    ResponseEntity<Double> getGPA(@PathVariable Long userId) {
        if (userRepository.findById(userId).isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        double sumCredits = 0;
        double sumScore = 0;

        for(Course course : userRepository.findById(userId).get().getCourses()) {
            List<Exams> exams = examsRepository.findExamsByUserIdAndCourseId(userId, course.getId());
            int mean = 0;
            if(!exams.isEmpty())
                mean = exams.stream().mapToInt(Exams::getScore).sum() / 3;
            sumCredits += course.getCredit();
            sumScore += getScoreGPA(mean) * course.getCredit();

        }
        return ResponseEntity.ok(sumScore / sumCredits);
    }

    static String getScoreLetter(int score) {
        if(score >= 90)
            return "AA";
        else if(score >= 85)
            return "BA";
        else if(score >= 80)
            return "BB";
        else if(score >= 70)
            return "CB";
        else if(score >= 60)
            return "CC";
        else if(score >= 55)
            return "DC";
        else if(score >= 50)
            return "DD";
        else if(score >= 40)
            return "FD";
        else
            return "FF";

    }

    static double getScoreGPA(int score) {
        if(score >= 90)
            return 4.0;
        else if(score >= 85)
            return 3.5;
        else if(score >= 80)
            return 3.0;
        else if(score >= 70)
            return 2.5;
        else if(score >= 60)
            return 2.0;
        else if(score >= 55)
            return 1.5;
        else if(score >= 50)
            return 1.0;
        else if(score >= 40)
            return 0.5;
        else
            return 0.0;

    }

}
