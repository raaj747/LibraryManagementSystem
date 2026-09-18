INSERT IGNORE INTO books
(isbn, title, author, category, total_quantity, available_quantity, status)
VALUES
('9780134685991', 'Effective Java', 'Joshua Bloch', 'Programming', 5, 5, 'AVAILABLE'),
('9780132350884', 'Clean Code', 'Robert C. Martin', 'Programming', 4, 4, 'AVAILABLE'),
('9781617294945', 'Spring in Action', 'Craig Walls', 'Java/Spring', 3, 3, 'AVAILABLE'),
('9781492078005', 'Java: The Complete Reference', 'Herbert Schildt', 'Java', 3, 3, 'AVAILABLE');
