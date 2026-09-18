-- Optional manual schema.
-- The Spring Boot application can create/update these tables automatically
-- because application.properties uses spring.jpa.hibernate.ddl-auto=update.

USE library_management;

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_users_role
        FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    isbn VARCHAR(30) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(150) NOT NULL,
    category VARCHAR(100),
    total_quantity INT NOT NULL,
    available_quantity INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_issues (
    id BIGINT NOT NULL AUTO_INCREMENT,
    book_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    issued_by BIGINT,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_issue_book
        FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT fk_issue_member
        FOREIGN KEY (member_id) REFERENCES users(id),
    CONSTRAINT fk_issue_librarian
        FOREIGN KEY (issued_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS fines (
    id BIGINT NOT NULL AUTO_INCREMENT,
    issue_id BIGINT NOT NULL UNIQUE,
    member_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME,
    paid_at DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_fine_issue
        FOREIGN KEY (issue_id) REFERENCES book_issues(id),
    CONSTRAINT fk_fine_member
        FOREIGN KEY (member_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fine_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(30) NOT NULL,
    payment_date DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_payment_fine
        FOREIGN KEY (fine_id) REFERENCES fines(id),
    CONSTRAINT fk_payment_member
        FOREIGN KEY (member_id) REFERENCES users(id)
);

INSERT IGNORE INTO roles (name) VALUES ('ADMIN'), ('LIBRARIAN'), ('MEMBER');

SHOW TABLES;
