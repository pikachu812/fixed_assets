CREATE TABLE assets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    purchase_date DATE,
    cost DECIMAL(10, 2),
    status VARCHAR(100),
    department VARCHAR(255)
);
