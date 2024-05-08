CREATE TABLE IF NOT EXISTS books(
	id IDENTITY NOT NULL PRIMARY KEY,
	title VARCHAR
);

CREATE TABLE IF NOT EXISTS users(
    id IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE IF NOT EXISTS reservations(
    id IDENTITY NOT NULL PRIMARY KEY,
    id_user BIGINT,
    id_book BIGINT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (id_user) references users(id),
    FOREIGN KEY (id_book) references books(id)
);

CREATE TABLE IF NOT EXISTS reviews(
    id IDENTITY NOT NULL PRIMARY KEY,
    id_user BIGINT,
    id_book BIGINT,
    rating INT,
    description TEXT,
    FOREIGN KEY (id_user) references users(id),
    FOREIGN KEY (id_book) references books(id)
);
