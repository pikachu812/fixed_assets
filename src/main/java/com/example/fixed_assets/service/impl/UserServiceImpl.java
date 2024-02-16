package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.UserDao;
import com.example.fixed_assets.entity.User;
import com.example.fixed_assets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int addUser(User user) {
        user.setPassword(md5(user.getPassword()));
        return userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {

        // 管理员修改用户信息时不修改密码
        // 用户修改自己的信息时不修改密码
        // 修改密码时需要通过其他接口实现
        user.setPassword(null);
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public void updateUserPassword(User user) {
        user.setPassword(md5(user.getPassword()));
        userDao.updateUserPassword(user);
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public User validateUser(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(md5(password))) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> searchUsers(User user) {
        return userDao.searchUsers(user);
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
