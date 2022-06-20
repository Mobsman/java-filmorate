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


SELECT f.id, f.name, f.description, f.duration, f.release_date, f.rating_id, r.name
FROM FILM f
         inner JOIN Rating r ON f.rating_id = r.id
where f.id = 1;

select g.id, g.name
from film f
         inner join genre_film gf on f.id = gf.FILM_ID
         inner join genre g on g.id = gf.GENRE_ID;

SELECT f.id, f.name, f.description, f.duration, f.release_date, f.rating_id, r.name
FROM FILM f
         inner JOIN Rating r;

WITH sm AS (
    SELECT film_id, COUNT(users_id) cnt
    FROM Likes
    GROUP BY film_id)
SELECT *
FROM Film
         JOIN sm ON (id = film_id)
ORDER by cnt DESC
LIMIT 10;

select g.id,g.name from film f inner join genre_film gf
                on f.id = 1 inner join genre g on g.id = gf.GENRE_ID;

select g.id,g.name from film f inner join genre_film gf on f.id = 1 inner join genre g on g.id = gf.GENRE_ID;

select genre_id from GENRE_FILM where film_id=1;

INSERT INTO GENRE_FILM (FILM_ID, GENRE_ID) VALUES (1,1);

