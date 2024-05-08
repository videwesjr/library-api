INSERT INTO books(title) VALUES
    ('Lord of the Rings: The Fellowship of the Ring'),
    ('Lord of the Rings: The Two Towers'),
    ('Lord of the Rings: The Return of the King'),
    ('The Silmarillion'),
    ('The Hobbit');

INSERT INTO users(name) VALUES
    ('User 1'),
    ('User 2'),
    ('User 3');


INSERT INTO reviews(id_book, id_user, rating, description) VALUES
    (1, 2, 7, 'Great'),
    (2, 1, 8, 'Awesome'),
    (2, 2, 10, 'Perfect');


INSERT INTO reservations(id_book, id_user, start_date, end_date) VALUES
    (1, 2, '2024-01-30', '2024-02-28'),
    (2, 1, '2024-01-10', '2024-02-10'),
    (2, 2, '2024-01-20', '2024-02-20'),
    (2, 3, '2024-05-20', '2024-06-20');
