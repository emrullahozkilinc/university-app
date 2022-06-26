package com.example.universityapp.controller;

import com.example.universityapp.dto.UserAvgDTO;
import com.example.universityapp.repo.UserRepository;
import com.example.universityapp.service.ScoreCalculator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("6. Soru için hazırlanan API")
@RestController
@RequestMapping("/courseUsers")
public class CourseUserController {

    UserRepository userRepository;
    ScoreCalculator scoreCalculator;

    public CourseUserController(UserRepository userRepository, ScoreCalculator scoreCalculator) {
        this.userRepository = userRepository;
        this.scoreCalculator = scoreCalculator;
    }

    @ApiOperation(value = "Verilen dersi alan kullanıcıların not ortalamasını ve notun harf değerini döndürür")
    @GetMapping("/avarageGrade/{courseId}")
    ResponseEntity<List<UserAvgDTO>> getAvarageGrade(@PathVariable Long courseId) {
        List<UserAvgDTO> res = userRepository.countAverageExamScoreByCourses(courseId);
        if(res.isEmpty())
            return null;
        res.forEach(x -> x.setScoreLetter(scoreCalculator.getScoreLetter((int) x.getAvg())));
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "100 üzerinden belli bir not ortalamasının üzerindeki öğrencileri döndürür.")
    @GetMapping("/greaterThan/{score}")
    ResponseEntity<List<UserAvgDTO>> getGreaterThen(@PathVariable("score") Integer score) {
        List<UserAvgDTO> res = userRepository.getUsersGreaterThenScore(score);
        if(res.isEmpty())
            return null;
        res.forEach(x -> x.setScoreLetter(scoreCalculator.getScoreLetter((int) x.getAvg())));
        return ResponseEntity.ok(res);
    }
}
