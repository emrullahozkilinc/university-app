package com.example.universityapp.mapper;

import com.example.universityapp.pojo.UserCourseScore;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserCourseScore> {


    @Override
    public UserCourseScore mapRow(ResultSet rs, int rowNum) throws SQLException {

        UserCourseScore user = new UserCourseScore(
                rs.getLong("user_id"),
                rs.getLong("course_id"),
                rs.getDouble("score")
        );

        return user;
    }
}
