package com.example.universityapp.service;

import com.example.universityapp.dto.CourseExamResultDTO;
import com.example.universityapp.pojo.UserCourseScore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreCalculator {

    public String getScoreLetter(int score) {
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

    public double getScoreGPA(int score) {
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

    public List<CourseExamResultDTO> calcBellCurve(List<UserCourseScore> userCourseScores, double score) {

        final ScoreCalc calc = i -> {
            if(score >= i+20)
                return "AA";
            else if(score >= i+15)
                return "BA";
            else if(score >= i+10)
                return "BB";
            else if(score >= i+5)
                return "CB";
            else if(score >= i)
                return "CC";
            else if(score >= i-5)
                return "DC";
            else if(score >= i-10)
                return "DD";
            else if(score >= i-15)
                return "FD";
            else
                return "FF";
        };

        return userCourseScores.stream()
                .map(userCourseScore -> new CourseExamResultDTO(userCourseScore.getUserId(), userCourseScore.getScore(), calc.getScoreLetter(userCourseScore.getScore())))
                .collect(java.util.stream.Collectors.toList());
    }


    @FunctionalInterface
    public interface ScoreCalc {
        String getScoreLetter(double score);
    }
}
