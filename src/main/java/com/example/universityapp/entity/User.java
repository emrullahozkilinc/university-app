package com.example.universityapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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