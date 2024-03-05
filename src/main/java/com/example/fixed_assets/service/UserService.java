package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    int addUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    void updateUserPassword(User user);

    User getUser(int id);

    User validateUser(String username, String password);

    List<User> searchUsers(Map<String, Object> params);


    boolean updatePassword(Integer userId, String oldPassword, String newPassword);
}
