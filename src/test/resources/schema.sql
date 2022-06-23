CREATE TABLE IF NOT EXISTS Rating
(
    id   serial PRIMARY KEY,
    name VARCHAR(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS Film
(
    id           serial PRIMARY KEY,
    name         VARCHAR(45)  NOT NULL,
    description  VARCHAR(600) NOT NULL,
    duration     INTEGER      NOT NULL,
    release_date TIMESTAMP    NOT NULL,
    rating_id    INT REFERENCES Rating (id)
);

CREATE TABLE IF NOT EXISTS Genre
(
    id   serial PRIMARY KEY,
    name VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS Genre_film
(
    film_id  INT REFERENCES Film (id) ,
    genre_id INT REFERENCES Genre (id)
);


CREATE TABLE IF NOT EXISTS Users
(
    id       serial PRIMARY KEY,
    email    VARCHAR(45) NOT NULL,
    login    VARCHAR(45) NOT NULL,
    name     VARCHAR(45) NOT NULL,
    birthday TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS Likes
(
    film_id  INT REFERENCES Film (id),
    users_id INT REFERENCES Users (id)
);


CREATE TABLE IF NOT EXISTS Friend
(
    User_id   INT REFERENCES Users (id),
    Friend_id INT REFERENCES Users (id)
);



