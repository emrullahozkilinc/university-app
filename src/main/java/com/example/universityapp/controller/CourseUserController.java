package com.example.universityapp.controller;

import com.example.universityapp.dto.CourseExamResultDTO;
import com.example.universityapp.entity.Course;
import com.example.universityapp.entity.Exams;
import com.example.universityapp.entity.User;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.universityapp.controller.ExamScoreController.getScoreLetter;

@Api("6. Soru için hazırlanan API")
@RestController
@RequestMapping("/courseUsers")
public class CourseUserController {

    UserRepository userRepository;
    CourseRepository courseRepository;
    ExamsRepository examsRepository;

    public CourseUserController(UserRepository userRepository, CourseRepository courseRepository, ExamsRepository examsRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.examsRepository = examsRepository;
    }

    @ApiOperation(value = "Verilen dersi alan kullanıcıların not ortalamasını ve notun harf değerini döndürür")
    @GetMapping("/avarageGrade/{courseId}")
    ResponseEntity<List<CourseExamResultDTO>> getAvarageGrade(@PathVariable Long courseId) {
        List<User> users = courseRepository.getReferenceById(courseId).getUsers();
        List<CourseExamResultDTO> resultDTOS = new ArrayList<>();

        for (User user : users) {
            int sum = 0;
            for (Course course : user.getCourses()) {
                List<Exams> exams = examsRepository.findExamsByUserIdAndCourseId(user.getId(), course.getId());


                if(exams != null & exams.size() != 0) {
                    sum += exams.stream().mapToInt(Exams::getScore).sum()/3;
                }else {
                    sum += 0;
                }
            }
            resultDTOS.add(new CourseExamResultDTO(user.getFirstname() + user.getLastname(), sum/user.getCourses().size(), getScoreLetter(sum/user.getCourses().size())));
        }
        return new ResponseEntity<>(resultDTOS, HttpStatus.OK);
    }
}
