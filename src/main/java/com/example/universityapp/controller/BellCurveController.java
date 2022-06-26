package com.example.universityapp.controller;

import com.example.universityapp.dao.ExamsDao;
import com.example.universityapp.dto.CourseExamResultDTO;
import com.example.universityapp.pojo.UserCourseScore;
import com.example.universityapp.service.ScoreCalculator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Api(value = "7. Soru için yazılan REST API")
@RestController
@RequestMapping("/bellcurve")
public class BellCurveController {

    ExamsDao examsDao;
    ScoreCalculator scoreCalculator;

    @Autowired
    public BellCurveController(ExamsDao examsDao, ScoreCalculator scoreCalculator) {
        this.examsDao = examsDao;
        this.scoreCalculator = scoreCalculator;
    }

    @ApiOperation(value = "Çan eğrisi hesaplama işlemi")
    @GetMapping("/getUserScore/{courseId}")
    ResponseEntity<List<CourseExamResultDTO>> getUsersScoreWithBellCurve(@PathVariable Long courseId) {

        List<UserCourseScore> userCourseScores = examsDao.getUserCourseScores(courseId);
        List<CourseExamResultDTO> resultDTOS;

        userCourseScores.sort(Comparator.comparing(UserCourseScore::getScore));
        if(userCourseScores.size() > 0) {
            double score = userCourseScores.get(userCourseScores.size()/2).getScore();
            if (score<60 && score>20) {
                resultDTOS = scoreCalculator.calcBellCurve(userCourseScores, score);
            } else
                return ResponseEntity.noContent().build();
        } else
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(resultDTOS);
    }
}
