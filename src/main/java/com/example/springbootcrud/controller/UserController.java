package com.example.springbootcrud.controller;

import com.example.springbootcrud.exception.UserNotFoundException;
import com.example.springbootcrud.model.User;
import com.example.springbootcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username){

            User user= userService.getUserByUsername(username);
            return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User newuser= userService.addUser(user);
        return new ResponseEntity<>(newuser,HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updatedUser=userService.updateUser(user);
        return new ResponseEntity<>(updatedUser,HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String username) {

            userService.deleteUser(username);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }
}
