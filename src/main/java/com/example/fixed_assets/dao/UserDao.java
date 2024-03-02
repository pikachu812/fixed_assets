package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.Employee;
import com.example.fixed_assets.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface UserDao {

    List<User> searchUsers(User user);
    User findByUsername(String username);

    int addUser(User user);

    void updateUser(User user);

    void updateUserPassword(User user);

    void deleteUser(int id);

    User getUser(int userId);


    /**
     * <p>根据指定的条件查询用户信息，包括其角色和权限。</p>
     *
     * <p>这个方法接受一个Map作为参数，该Map可以包含以下键值对：</p>
     * <p>- userId (Integer): 要查询的用户ID。</p>
     * <p>- username (String): 要查询的用户名。</p>
     * <p>- roleName (String): 要过滤的角色名称。</p>
     * <p>- permissionName (String): 要过滤的权限名称。</p>
     * <p>- offset (Integer): 分页查询的起始位置。</p>
     * <p>- limit (Integer): 分页查询的记录数量。</p>
     *
     * <p>
     * 返回值是一个Map，包含用户信息、角色信息和权限信息。返回的Map结构将反映查询结果。
     * </p>
     *
     * @param map 包含查询条件的Map对象。
     * @return 包含用户信息、角色信息和权限信息的Map对象。
     */
    List<User> findUserWithRoleAndPermissions(Map<String,Object> map);

    void deleteEmployee(int employeeId);

    void addEmployee(Employee employee);

    void updateEmployee(Employee employee);
}