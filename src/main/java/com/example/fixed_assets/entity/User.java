/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.fixed_assets.entity;

import java.util.List;

/**
 * 用户实体类
 */
@lombok.Data
public class User {

    private Integer userId;
    private String username;
    private String password;
    private int roleId; // 关联角色ID
    private Role role; // 关联角色对象
    private List<Permission> permissions; // 关联权限对象

}
