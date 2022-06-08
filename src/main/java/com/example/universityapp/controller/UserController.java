package com.example.universityapp.controller;

import com.example.universityapp.entity.User;
import com.example.universityapp.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "1. Soru için yazılan REST API")
@RestController
@RequestMapping("/user")
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Öğrenci ekleme işlemi")
    @PostMapping("/addUser")
    ResponseEntity<String> addUser(@RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<>("User added.", HttpStatus.OK);
    }

    @ApiOperation(value = "Öğrenci düzenleme işlemi")
    @PutMapping("/updateUser/{id}")
    ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable Long id){
        if(userRepository.findById(id).isPresent()){
            user.setId(id);
            userRepository.save(user);
            return new ResponseEntity<>("User updated.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Öğrenci silme işlemi")
    @DeleteMapping("/deleteUser/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return new ResponseEntity<>("User deleted.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Tekil öğrenci getirme işlemi")
    @GetMapping("/getUser/{id}")
    ResponseEntity<User> getUser(@PathVariable Long id){
        if(userRepository.findById(id).isPresent()){
            return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Öğrenci listeleme işlemi")
    @GetMapping("/getAllUsers")
    ResponseEntity<Iterable<User>> getAllUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}
