package com.example.universityapp.controller;

import com.example.universityapp.entity.Exams;
import com.example.universityapp.repo.CourseRepository;
import com.example.universityapp.repo.ExamsRepository;
import com.example.universityapp.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "4. Soru için yazılan REST API")
@RestController
@RequestMapping("/exam")
public class ExamController {

    ExamsRepository examsRepository;
    UserRepository userRepository;
    CourseRepository courseRepository;

    public ExamController(ExamsRepository examsRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.examsRepository = examsRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @ApiOperation(value = "Öğrencilerin sınav notlarını ekleme işlemi")
    @PostMapping("/addExam")
    ResponseEntity<String> addExam(@RequestBody Exams exams){
        if (exams.getScore() < 0 || exams.getScore() > 100) {
            return new ResponseEntity<>("Score must be between 0-100.",HttpStatus.BAD_REQUEST);
        }else if(examsRepository.findExamByCourseIdAndUserIdAndExamId(exams.getCourseId(), exams.getUserId(), exams.getExamId()) != null){
            return new ResponseEntity<>("This student already have score for that exam.", HttpStatus.CONFLICT);
        }else if(!userRepository.getReferenceById(exams.getUserId()).getCourses().contains(courseRepository.getReferenceById(exams.getCourseId()))){
            return new ResponseEntity<>("This does not have this course.", HttpStatus.BAD_REQUEST);
        }else
            examsRepository.save(exams);
        return new ResponseEntity<>("Exam added.", HttpStatus.OK);
    }

}
