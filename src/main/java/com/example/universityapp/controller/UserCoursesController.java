package com.example.universityapp.controller;

import com.example.universityapp.entity.Course;
import com.example.universityapp.entity.User;
import com.example.universityapp.repo.CourseRepository;
import com.example.universityapp.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usercourses")
@Api(value = "3. Soru için yazılan REST API")
public class UserCoursesController {

    UserRepository userRepository;
    CourseRepository courseRepository;

    public UserCoursesController(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }


    @ApiOperation(value = "Derse öğrenci ekleme işlemi")
    @PostMapping("/addUserCourse/{userId}/{courseId}")
    ResponseEntity<String> addUserCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        User user;
        Course course;

        if(courseRepository.findById(courseId).isEmpty() || userRepository.findById(userId).isEmpty()) {
            return new ResponseEntity<>("User or course not found.", HttpStatus.NOT_FOUND);
        }else {
            user = userRepository.findById(userId).get();
            course = courseRepository.findById(courseId).get();
        }

        if(user.getCourses().contains(course)) {
            return new ResponseEntity<>("User already has this course.", HttpStatus.BAD_REQUEST);
        }else {
            user.addCourse(course);
            userRepository.save(user);
            return new ResponseEntity<>("User course added.", HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Dersten öğrenci kaldırma işlemi")
    @DeleteMapping ("/deleteUserCourse/{userId}/{courseId}")
    ResponseEntity<String> deleteUserCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        User user;
        Course course;

        if(courseRepository.findById(courseId).isEmpty() || userRepository.findById(userId).isEmpty()) {
            return new ResponseEntity<>("User or course not found.", HttpStatus.NOT_FOUND);
        }else {
            user = userRepository.findById(userId).get();
            course = courseRepository.findById(courseId).get();
        }

        if(!user.getCourses().contains(course)) {
            return new ResponseEntity<>("User does not already have this course.", HttpStatus.BAD_REQUEST);
        }else {
            user.removeCourse(course);
            userRepository.save(user);
            return new ResponseEntity<>("User course deleted.", HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Öğrencinin derslerini getirme işlemi")
    @GetMapping("/getUserCourses/{userId}")
    ResponseEntity<Iterable<Course>> getUserCourses(@PathVariable Long userId) {
        User user;

        if(userRepository.findById(userId).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
            user = userRepository.findById(userId).get();
        }

        return new ResponseEntity<>(user.getCourses(), HttpStatus.OK);
    }

    @ApiOperation(value = "Dersin öğrencilerini getirme işlemi")
    @GetMapping("/getCourseUsers/{courseId}")
    ResponseEntity<Iterable<User>> getCourseUsers(@PathVariable Long courseId) {
        Course course;

        if(courseRepository.findById(courseId).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
            course = courseRepository.findById(courseId).get();
        }

        return new ResponseEntity<>(course.getUsers(), HttpStatus.OK);
    }
}
