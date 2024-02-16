package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.User;
import com.example.fixed_assets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(String username, String password, HttpSession session) {
        User user = userService.validateUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return ResponseEntity.ok("Login successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid username or password.");
    }


    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully.");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<List<User>> searchUsers(@RequestBody User user) {
        List<User> users = userService.searchUsers(user);
        return ResponseEntity.ok(users);
    }
}

