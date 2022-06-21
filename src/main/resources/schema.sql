CREATE TABLE Rating
(
    id   serial PRIMARY KEY,
    name VARCHAR(6) NOT NULL
);

CREATE TABLE Film
(
    id           serial PRIMARY KEY,
    name         VARCHAR(45)  NOT NULL,
    description  VARCHAR(600) NOT NULL,
    duration     INTEGER      NOT NULL,
    release_date TIMESTAMP    NOT NULL,
    rating_id    INT REFERENCES Rating (id)
);

CREATE TABLE Genre
(
    id   serial PRIMARY KEY,
    name VARCHAR(15) NOT NULL
);

CREATE TABLE Genre_film
(
    film_id  INT REFERENCES Film (id) ,
    genre_id INT REFERENCES Genre (id)
);


CREATE TABLE Users
(
    id       serial PRIMARY KEY,
    email    VARCHAR(45) NOT NULL,
    login    VARCHAR(45) NOT NULL,
    name     VARCHAR(45) NOT NULL,
    birthday TIMESTAMP   NOT NULL
);

CREATE TABLE Likes
(
    film_id  INT REFERENCES Film (id),
    users_id INT REFERENCES Users (id)
);


CREATE TABLE Friend
(
    User_id   INT REFERENCES Users (id),
    Friend_id INT REFERENCES Users (id)
);



INSERT into Rating (name)
VALUES ('G'),
       ('PG'),
       ('PG-13'),
       ('R'),
       ('NC-17');

INSERT into Genre (name)
VALUES ('Комедия'),
       ('Драма'),
       ('Мультфильм'),
       ('Триллер'),
       ('Документальный'),
       ('Боевик');

INSERT INTO USERS
(EMAIL, LOGIN, NAME, BIRTHDAY)
VALUES ('test@test.com', 'test1', 'user1', '1999-11-5'),
       ('test@test.com', 'test2', 'user2', '1988-11-5'),
       ('test@test.com', 'test3', 'user3', '1977-11-5');

INSERT INTO FILM
              (NAME, DESCRIPTION, DURATION, RELEASE_DATE, RATING_id)
VALUES ('test-film', 'about test', 18, '1993-1-11', 5),
       ('test-film2', 'about test', 18, '1994-2-22', 5);

