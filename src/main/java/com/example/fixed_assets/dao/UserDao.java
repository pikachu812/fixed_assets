package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserDao {

    List<User> searchUsers(User user);
    User findByUsername(String username);

    int addUser(User user);

    void updateUser(User user);

    void updateUserPassword(User user);

    void deleteUser(int id);

    User getUser(int id);

}