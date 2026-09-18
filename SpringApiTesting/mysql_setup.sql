-- Run this script in MySQL Workbench, DBeaver, or the MySQL CLI.

CREATE DATABASE IF NOT EXISTS library_management
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE library_management;

-- Optional: use a separate application user instead of root.
-- If you want this, uncomment these lines:
--
-- CREATE USER IF NOT EXISTS 'library_app'@'localhost'
--     IDENTIFIED BY 'Library@123';
-- GRANT ALL PRIVILEGES ON library_management.* TO 'library_app'@'localhost';
-- FLUSH PRIVILEGES;

SHOW DATABASES;
