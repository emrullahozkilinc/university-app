package com.example.universityapp.controller;

import com.example.universityapp.dto.UserAvgDTO;
import com.example.universityapp.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.universityapp.controller.ExamScoreController.getScoreLetter;

@Api("6. Soru için hazırlanan API")
@RestController
@RequestMapping("/courseUsers")
public class CourseUserController {

    UserRepository userRepository;

    public CourseUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Verilen dersi alan kullanıcıların not ortalamasını ve notun harf değerini döndürür")
    @GetMapping("/avarageGrade/{courseId}")
    ResponseEntity<List<UserAvgDTO>> getAvarageGrade(@PathVariable Long courseId) {
        List<UserAvgDTO> res = userRepository.countAverageExamScoreByCourses(courseId);
        if(res.isEmpty())
            return null;
        res.forEach(x -> x.setScoreLetter(getScoreLetter((int) x.getAvg())));
        return ResponseEntity.ok(res);
    }

    @ApiOperation(value = "100 üzerinden belli bir not ortalamasının üzerindeki öğrencileri döndürür.")
    @GetMapping("/greaterThan/{score}")
    ResponseEntity<List<UserAvgDTO>> getGreaterThen(@PathVariable Integer score) {
        List<UserAvgDTO> res = userRepository.getUsersGreaterThenScore(score);
        if(res.isEmpty())
            return null;
        res.forEach(x -> x.setScoreLetter(getScoreLetter((int) x.getAvg())));
        return ResponseEntity.ok(res);
    }
}
