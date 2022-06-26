package com.example.universityapp.dao;

import com.example.universityapp.mapper.UserMapper;
import com.example.universityapp.pojo.UserCourseScore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ExamsDao {

    JdbcTemplate jdbcTemplate;

    public ExamsDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<UserCourseScore> getUserCourseScores(long courseId) {
        String sql = "select user_id, course_id, sum(IfNULL(score,0))/3 as score\n" +
                "from exams\n" +
                "where course_id = ?\n" +
                "group by user_id;";
        List<UserCourseScore> userCourseScores = jdbcTemplate.query(sql, new UserMapper(), courseId);
        return userCourseScores;
    }
}
