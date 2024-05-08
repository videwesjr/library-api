package com.project.libraryApi.controller;

import com.project.libraryApi.dto.UserDTO;
import com.project.libraryApi.model.User;
import com.project.libraryApi.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
        log.info("Receive request to GET ALL users");
        return new ResponseEntity<>(
                userService.getAllUsers(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Receive request to GET user. id: ".concat(id.toString()));
        User user = userService.getUserById(id);
        log.info("User find success: ".concat(user.toString()));
        return new ResponseEntity<>(
                user,
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserDTO user) {
        log.info("Receive request to INSERT user: ".concat(user.toString()));
        return new ResponseEntity<>(
                userService.saveUser(user),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO user) {
        log.info("Receive request to UPDATE user. id: ".concat(id.toString()).concat(" user: ".concat(user.toString())));
        return new ResponseEntity<>(
                userService.updateUser(id, user),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        log.info("Receive request to DELETE user. id: ".concat(id.toString()));
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
