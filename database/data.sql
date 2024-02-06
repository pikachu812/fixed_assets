create database if not exists fixed_assets
    default character set utf8;

-- 部门表
CREATE TABLE department (
                            department_id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(100) NOT NULL,
                            description TEXT
);

-- 员工表
CREATE TABLE employee (
                          employee_id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(100) NOT NULL,
                          department_id INT,
                          FOREIGN KEY (department_id) REFERENCES department(department_id)
);

-- 固定资产类型表
CREATE TABLE asset_type (
                            asset_type_id INT PRIMARY KEY AUTO_INCREMENT,
                            type_name VARCHAR(100) NOT NULL,
                            description TEXT
);

-- 固定资产表
CREATE TABLE fixed_asset (
                             asset_id INT PRIMARY KEY AUTO_INCREMENT,
                             asset_type_id INT,
                             name VARCHAR(100) NOT NULL,
                             purchase_date DATE,
                             price DECIMAL(10, 2),
                             status VARCHAR(50),
                             FOREIGN KEY (asset_type_id) REFERENCES asset_type(asset_type_id)
);

-- 固定资产领用表
CREATE TABLE asset_allocation (
                                  allocation_id INT PRIMARY KEY AUTO_INCREMENT,
                                  asset_id INT,
                                  employee_id INT,
                                  allocation_date DATE,
                                  return_date DATE,
                                  FOREIGN KEY (asset_id) REFERENCES fixed_asset(asset_id),
                                  FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

-- 固定资产维修表
CREATE TABLE asset_repair (
      repair_id INT PRIMARY KEY AUTO_INCREMENT,
      asset_id INT,
      repair_date DATE,
      cost DECIMAL(10, 2),
      details TEXT,
      FOREIGN KEY (asset_id) REFERENCES fixed_asset(asset_id)
);

-- 固定资产报废表
CREATE TABLE asset_scrap (
     scrap_id INT PRIMARY KEY AUTO_INCREMENT,
     asset_id INT,
     scrap_date DATE,
     reason TEXT,
     FOREIGN KEY (asset_id) REFERENCES fixed_asset(asset_id)
);

-- 固定资产折旧表
CREATE TABLE asset_depreciation (
    depreciation_id INT PRIMARY KEY AUTO_INCREMENT,
    asset_id INT,
    depreciation_date DATE,
    amount DECIMAL(10, 2),
    FOREIGN KEY (asset_id) REFERENCES fixed_asset(asset_id)
);

-- 固定资产盘点表
CREATE TABLE asset_inventory (
     inventory_id INT PRIMARY KEY AUTO_INCREMENT,
     asset_id INT,
     inventory_date DATE,
     status VARCHAR(50),
     FOREIGN KEY (asset_id) REFERENCES fixed_asset(asset_id)
);

-- 用户表
CREATE TABLE user (
      user_id INT PRIMARY KEY AUTO_INCREMENT,
      username VARCHAR(100) NOT NULL,
      password VARCHAR(100) NOT NULL,
      role VARCHAR(50)
);

-- 数据统计可能需要根据具体需求进一步定义。
