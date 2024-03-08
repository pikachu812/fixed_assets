create table asset_type
(
    asset_type_id int auto_increment
        primary key,
    type_name     varchar(100) not null,
    description   text         null
);

create table department
(
    department_id int auto_increment
        primary key,
    name          varchar(100) not null,
    description   text         null
);

create table employee
(
    employee_id   int auto_increment
        primary key,
    name          varchar(100) not null,
    department_id int          null,
    constraint employee_ibfk_1
        foreign key (department_id) references department (department_id)
);

create index department_id
    on employee (department_id);

create table fixed_asset
(
    asset_id      int auto_increment
        primary key,
    asset_type_id int            null,
    name          varchar(100)   not null,
    purchase_date date           null,
    price         decimal(10, 2) null,
    status        varchar(50)    null,
    img_dir       varchar(200)   null,
    useful_year   int            null,
    constraint fixed_asset_ibfk_1
        foreign key (asset_type_id) references asset_type (asset_type_id)
);

create table asset_depreciation
(
    depreciation_id          int auto_increment
        primary key,
    asset_id                 int            null,
    depreciation_date        date           null,
    depreciation_amount      decimal(10, 2) null,
    accumulated_depreciation decimal(10, 2) null,
    book_value               decimal(10, 2) null,
    constraint asset_depreciation_ibfk_1
        foreign key (asset_id) references fixed_asset (asset_id)
);

create index asset_id
    on asset_depreciation (asset_id);

create table asset_inventory
(
    inventory_id   int auto_increment
        primary key,
    asset_id       int            null,
    inventory_date date           null,
    department_id  int            null,
    book_quantity  int            null,
    book_value     decimal(10, 2) null,
    inventory_name varchar(200)   null,
    constraint asset_inventory_ibfk_1
        foreign key (asset_id) references fixed_asset (asset_id)
);

create index asset_id
    on asset_inventory (asset_id);

create table asset_repair
(
    repair_id   int auto_increment
        primary key,
    asset_id    int                          null,
    repair_date date                         null,
    status      varchar(20) default '待维修' null comment '当前维修记录状态',
    cost        decimal(10, 2)               null,
    details     text                         null,
    constraint asset_repair_ibfk_1
        foreign key (asset_id) references fixed_asset (asset_id)
);

create index asset_id
    on asset_repair (asset_id);

create table asset_scrap
(
    scrap_id   int auto_increment
        primary key,
    asset_id   int  null,
    scrap_date date null,
    reason     text null,
    constraint asset_scrap_ibfk_1
        foreign key (asset_id) references fixed_asset (asset_id)
);

create index asset_id
    on asset_scrap (asset_id);

create index asset_type_id
    on fixed_asset (asset_type_id);

create table permission
(
    permission_id   int auto_increment
        primary key,
    permission_name varchar(100) not null,
    description     text         null
);

create table role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(50) not null
);

create table role_permission
(
    role_id       int not null,
    permission_id int not null,
    primary key (role_id, permission_id),
    constraint role_permission_ibfk_1
        foreign key (role_id) references role (role_id),
    constraint role_permission_ibfk_2
        foreign key (permission_id) references permission (permission_id)
);

create index permission_id
    on role_permission (permission_id);

create table user
(
    user_id     int auto_increment
        primary key,
    username    varchar(50)  not null,
    password    varchar(255) not null,
    role_id     int          null,
    employee_id int          null,
    constraint user_pk
        unique (username),
    constraint user_ibfk_1
        foreign key (role_id) references role (role_id),
    constraint user_ibfk_2
        foreign key (employee_id) references employee (employee_id)
);

create table asset_allocation
(
    allocation_id          int auto_increment
        primary key,
    asset_id               int                          null,
    user_id                int                          null,
    department             varchar(50)                  null,
    allocation_date        date                         null,
    return_date            date                         null,
    allocation_description varchar(200)                 null,
    status                 varchar(50) default '待审核' null,
    reason                 varchar(200)                 null,
    constraint asset_allocation_ibfk_1
        foreign key (asset_id) references fixed_asset (asset_id),
    constraint asset_allocation_ibfk_2
        foreign key (user_id) references user (user_id)
);

create index asset_id
    on asset_allocation (asset_id);

create index user_id
    on asset_allocation (user_id);

create index role_id
    on user (role_id);

