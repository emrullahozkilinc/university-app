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
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name ="courseName")
    String courseName;

    @Column(name ="credit")
    int credit;

    @JsonIgnore  //to avoid infinite loop
    @ManyToMany(mappedBy = "courses")
    List<User> users;

    void addUser(User user){
        if(users == null)
            users = new ArrayList<>();
        else
            users.add(user);
    }
}