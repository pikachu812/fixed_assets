package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.UserDao;
import com.example.fixed_assets.entity.Employee;
import com.example.fixed_assets.entity.User;
import com.example.fixed_assets.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public int addUser(User user) {
        user.setPassword(md5(user.getPassword()));
        //如果是普通用户
        if (user.getRoleId() == 2) {
            if(user.getEmployee().getName() == null || user.getEmployee().getName().isEmpty()){
                throw new IllegalArgumentException("Employee name cannot be empty.");
            }
            if(user.getEmployee().getDepartmentId() == null){
                throw new IllegalArgumentException("Employee department cannot be empty.");
            }

            userDao.addEmployee(user.getEmployee());
            user.setEmployeeId(user.getEmployee().getEmployeeId());
            logger.info(user.toString());

        }

        return userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {

        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            user.setPassword(md5(user.getPassword()));
            userDao.updateUserPassword(user);
        }

        // 管理员修改用户信息时不修改密码
        // 用户修改自己的信息时不修改密码
        // 修改密码时需要通过其他接口实现
        user.setPassword(null);

        User oldUser = userDao.getUser(user.getUserId());




        if(user.getRoleId() == 1 && oldUser.getEmployeeId() != null){

            int employeeId = oldUser.getEmployeeId();

            // 修改为管理员，需要删除员工信息
            user.setEmployeeId(null);
            user.setEmployee(null);
            userDao.updateUser(user);
            userDao.deleteEmployee(employeeId);

        }else if(user.getRoleId() == 2 && oldUser.getEmployeeId() == null){
            // 修改为普通用户，需要添加员工信息
            if(user.getEmployee().getName() == null || user.getEmployee().getName().isEmpty()){
                throw new IllegalArgumentException("Employee name cannot be empty.");
            }
            if(user.getEmployee().getDepartmentId() == null){
                throw new IllegalArgumentException("Employee department cannot be empty.");
            }
            userDao.addEmployee(user.getEmployee());
            user.setEmployeeId(user.getEmployee().getEmployeeId());

            userDao.updateUser(user);
        } else if(user.getRoleId() == 2 && oldUser.getEmployeeId() != null){

            // 保持一致性，防止user.getEmployee().employeeId为空
            user.getEmployee().setEmployeeId(oldUser.getEmployeeId());
            // 修改普通用户信息
            userDao.updateEmployee(user.getEmployee());

            userDao.updateUser(user);
        }


    }

    @Override
    @Transactional
    public void deleteUser(int id) {

        User user = userDao.getUser(id);

        if (user.getEmployeeId() != null) {
            userDao.deleteEmployee(user.getEmployeeId());
        }
        userDao.deleteUser(id);
    }

    @Override
    public void updateUserPassword(User user) {

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        user.setPassword(md5(user.getPassword()));
        userDao.updateUserPassword(user);
    }

    @Override
    public User getUser(int userId) {

        User user = userDao.getUser(userId);
        if(user.getEmployee() == null){
            user.setEmployee(new Employee());
        }

        return user;
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
    public List<User> searchUsers(Map<String, Object> params){

        List<User> users =  userDao.findUserWithRoleAndPermissions(params);


        // 保证数据即使不存在也不能为null，防止前端报错
        for (User user : users) {
            if(user.getEmployee() == null){
                user.setEmployee(new Employee());
            }
        }

        return users;
    }

    @Override
    public boolean updatePassword(Integer userId, String oldPassword, String newPassword) {

        User user = userDao.getUser(userId);
        if (user.getPassword().equals(md5(oldPassword))) {
            user.setPassword(md5(newPassword));
            userDao.updateUserPassword(user);
            return true;
        }

        return false;
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
