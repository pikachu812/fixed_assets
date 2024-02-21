INSERT INTO `role` (role_name) VALUES ('管理员');
INSERT INTO `role` (role_name) VALUES ('普通用户');

INSERT INTO `user` (username, password, role_id) VALUES ('user1', 'password1', 1);
INSERT INTO `user` (username, password, role_id) VALUES ('user2', 'password2', 2);



INSERT INTO `permission` (permission_name, description) VALUES ('创建用户', '允许用户创建新用户');
INSERT INTO `permission` (permission_name, description) VALUES ('查看用户', '允许用户查看用户信息');
INSERT INTO `permission` (permission_name, description) VALUES ('更新用户', '允许用户更新用户信息');
INSERT INTO `permission` (permission_name, description) VALUES ('删除用户', '允许用户删除用户');

-- 假设管理员有所有权限
INSERT INTO `role_permission` (role_id, permission_id) VALUES (1, 1);
INSERT INTO `role_permission` (role_id, permission_id) VALUES (1, 2);
INSERT INTO `role_permission` (role_id, permission_id) VALUES (1, 3);
INSERT INTO `role_permission` (role_id, permission_id) VALUES (1, 4);

-- 假设普通用户只有查看权限
INSERT INTO `role_permission` (role_id, permission_id) VALUES (2, 2);
