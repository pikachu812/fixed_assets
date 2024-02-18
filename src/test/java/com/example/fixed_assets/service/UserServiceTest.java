package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private  static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    private User testUser;

    @Before
    public void setUp() {
        logger.info(() -> "setUp");

        testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword("test");
        testUser.setRoleId(1);
    }


    @Test
    public void test(){
        addUser();
        getUser();
        updateUser();
        updateUserPassword();
        searchUsers();
        validateUser();
        deleteUser();
    }


    public void addUser() {
        logger.info(() -> "------------------>addUser");
        userService.addUser(testUser);
        logger.info(() -> "userId: " + testUser.getUserId());
    }

    public void getUser() {
        logger.info(() -> "------------------>getUser");
        User user = userService.getUser(testUser.getUserId());
        logger.info(() -> "user: " + user);
    }

    public void updateUser() {
        logger.info(() -> "------------------>updateUser");
        testUser.setUsername("test2");
        userService.updateUser(testUser);
    }

    public void updateUserPassword() {
        logger.info(() -> "------------------>updateUserPassword");
        testUser.setPassword("test2");
        userService.updateUserPassword(testUser);
    }

    public void searchUsers() {
        logger.info(() -> "------------------>searchUsers");
        Map<String, Object> params = new HashMap<>();
        // 添加查询参数
        List<User> users = userService.searchUsers(params);
        logger.info(() -> "users: " + users);
        assert users != null && !users.isEmpty();
    }

    public void validateUser() {
        logger.info(() -> "------------------>validateUser");
        User user = userService.validateUser("test2", "test2");
        logger.info(() -> "user: " + user);
    }

    public void deleteUser() {
        logger.info(() -> "------------------>deleteUser");
        userService.deleteUser(testUser.getUserId());
    }

}
