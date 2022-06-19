package com.example.universityapp.entity;

import com.example.universityapp.dto.UserAvgDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getUserAvgByCourseId",
                query = "select concat(firstname, ' ', lastname) as name, round(sum(avarage*credit)/sum(credit),2) as avg from\n" +
                        "    (\n" +
                        "        select userid , users.firstname, users.lastname, course.course_name, credit, sum(IfNULL(score,0))/3 as avarage\n" +
                        "        from users\n" +
                        "                 right join usercourses on users.id = usercourses.userid\n" +
                        "                 left join course on usercourses.courseid = course.id\n" +
                        "                 left join exams on usercourses.courseid = exams.course_id\n" +
                        "            and usercourses.userid = exams.user_id\n" +
                        "        where users.id in (select userid from usercourses where courseid = :id)\n" +
                        "        group by course_id\n" +
                        "    )\n" +
                        "group by userid;",
                resultSetMapping = "avgByCourseID"
        ),
        @NamedNativeQuery(
                name = "getGreaterThen",
                query = "select concat(firstname, ' ', lastname) as name, round(sum(avarage*credit)/sum(credit),2) as avg from\n" +
                        "    (\n" +
                        "        select userid , users.firstname, users.lastname, course.course_name, credit, sum(IfNULL(score,0))/3 as avarage\n" +
                        "        from users\n" +
                        "                 right join usercourses on users.id = usercourses.userid\n" +
                        "                 left join course on usercourses.courseid = course.id\n" +
                        "                 left join exams on usercourses.courseid = exams.course_id\n" +
                        "            and usercourses.userid = exams.user_id\n" +
                        "        group by course_id\n" +
                        "    )\n" +
                        "group by userid\n" +
                        "having avg > :score",
                resultSetMapping = "avgByCourseID"
        )
})
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "avgByCourseID",
                classes = @ConstructorResult(targetClass = UserAvgDTO.class,
                        columns = {
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "avg", type = Double.class)
                        })),
        @SqlResultSetMapping(name = "greaterThan",
                classes = @ConstructorResult(targetClass = UserAvgDTO.class,
                        columns = {
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "avg", type = Double.class)
                        }))}
)
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