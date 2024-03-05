package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.User;
import com.example.fixed_assets.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     * @param requestUser 用户信息
     * @param session session
     * @return 如果登录成功，返回用户信息，否则返回错误信息
     *         错误码：400
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User requestUser, HttpSession session) {


        String username = requestUser.getUsername();
        String password = requestUser.getPassword();

        logger.info("username: " + username);
        logger.info("password: " + password);

        User user = userService.validateUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            //返回用户信息，以json格式
            return ResponseEntity.ok(user);
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

    @GetMapping("/my")
    public ResponseEntity<?> getMyUser(HttpSession session) {
        User user = (User) session.getAttribute("user");

        //如果用户未登录，返回错误信息
        if (user == null) {
            return ResponseEntity.badRequest().body("请重新登录！");
        }

        user = userService.getUser(user.getUserId());

        return ResponseEntity.ok(user);
    }

    @PostMapping("updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().body("请重新登录！");
        }
        String oldPassword = map.get("old");
        String newPassword = map.get("new");
        if (userService.updatePassword(user.getUserId(), oldPassword, newPassword)) {
            return ResponseEntity.ok("密码修改成功！");
        }
        return ResponseEntity.badRequest().body("原密码错误！");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/details")
    public ResponseEntity<List<User>> searchUsers(@RequestBody Map<String,Object> user) {
        List<User> users = userService.searchUsers(user);
        return ResponseEntity.ok(users);
    }
}

