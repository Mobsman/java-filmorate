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