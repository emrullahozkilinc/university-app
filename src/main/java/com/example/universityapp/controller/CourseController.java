package com.example.universityapp.controller;

import com.example.universityapp.entity.Course;
import com.example.universityapp.repo.CourseRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "2. Soru için yazılan REST API")
@RestController
@RequestMapping("/course")
public class CourseController {

    CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @ApiOperation(value = "Ders ekleme işlemi")
    @PostMapping("/addCourse")
    ResponseEntity<String> addCourse(@RequestBody Course course){
        if (course.getCredit() < 1 || course.getCredit() > 10) {
            return new ResponseEntity<>("Credit cannot be negative or greater than 10.", HttpStatus.BAD_REQUEST);
        }

        courseRepository.save(course);
        return new ResponseEntity<>("Course added.", HttpStatus.OK);
    }

    @ApiOperation(value = "Ders düzenleme işlemi")
    @PutMapping("/updateCourse/{id}")
    ResponseEntity<String> updateCourse(@RequestBody Course course, @PathVariable Long id){
        if(courseRepository.findById(id).isPresent()){
            course.setId(id);
            courseRepository.save(course);
            return new ResponseEntity<>("Course updated.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Course not found.", HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Ders silme işlemi")
    @DeleteMapping("/deleteCourse/{id}")
    ResponseEntity<String> deleteCourse(@PathVariable Long id){
        if(courseRepository.findById(id).isPresent()){
            courseRepository.deleteById(id);
            return new ResponseEntity<>("Course deleted.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Course not found.", HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Tekil ders getirme işlemi")
    @GetMapping("/getCourse/{id}")
    ResponseEntity<Course> getCourse(@PathVariable Long id){
        if(courseRepository.findById(id).isPresent()){
            return new ResponseEntity<>(courseRepository.findById(id).get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Dersleri listeleme işlemi")
    @GetMapping("/getAllCourses")
    ResponseEntity<Iterable<Course>> getAllCourses(){
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }
}
