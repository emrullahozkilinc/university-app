package com.example.universityapp.controller;

import com.example.universityapp.dao.ExamsDao;
import com.example.universityapp.pojo.UserCourseScore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    ExamsDao examsDao;

    @Autowired
    public BellCurveController(ExamsDao examsDao) {
        this.examsDao = examsDao;
    }

    @ApiOperation(value = "Çan eğrisi hesaplama işlemi")
    @GetMapping("/getUserScore/{courseId}")
    ResponseEntity<List<UserCourseScore>> getUsersScoreWithBellCurve(@PathVariable Long courseId) {
        return new ResponseEntity<>(examsDao.getUserCourseScores(courseId), HttpStatus.OK);
    }
}
