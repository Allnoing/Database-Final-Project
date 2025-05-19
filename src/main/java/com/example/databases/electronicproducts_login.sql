-- Uses electronic products DB --
use electronicproducts;

-- Users table for electronicproducts login --
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

-- Inserts premade users including username and hashed password --
INSERT INTO users (username, password_hash) 
VALUES 
('admin', SHA2('password123', 256)),
('sbonner3', SHA2('Passsword01', 256));
