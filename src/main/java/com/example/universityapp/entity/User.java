package com.example.universityapp.entity;

import com.example.universityapp.dto.UserAvgDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getUserAvgByCourseId",
                query = "select concat(firstname, ' ', lastname) as name, avg(avarage) as avg from\n" +
                        "    (\n" +
                        "        select userid , users.firstname, users.lastname, course.course_name, avg(score) as avarage\n" +
                        "        from users\n" +
                        "                 join usercourses on users.id = usercourses.userid\n" +
                        "                 join course on usercourses.courseid = course.id\n" +
                        "                 join exams on usercourses.courseid = exams.course_id\n" +
                        "            and usercourses.userid = exams.user_id\n" +
                        "        where users.id in (select userid from usercourses where courseid = :id)\n" +
                        "        group by course_id\n" +
                        "    )\n" +
                        "group by userid;",
                resultSetMapping = "avgByCourseID"
        )
})
@SqlResultSetMapping(name = "avgByCourseID",
        classes = @ConstructorResult(targetClass = UserAvgDTO.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "avg", type = Double.class)
                }))
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "firstname")
    String firstname;

    @Column(name = "lastname")
    String lastname;

    @JsonIgnore  //to avoid infinite loop
    @ManyToMany
    @JoinTable(
            name = "usercourses",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "courseid"))
    List<Course> courses;

    public void addCourse(Course course){
        if(courses == null)
            courses = new ArrayList<>();
        else
            courses.add(course);
    }

    public void removeCourse(Course course){
        if(courses != null)
            courses.remove(course);
    }
}