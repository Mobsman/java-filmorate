CREATE TABLE Rating (
                        id serial PRIMARY KEY,
                        name VARCHAR(6) NOT NULL);

CREATE TABLE Film (
                      id serial PRIMARY KEY,
                      name VARCHAR(45) NOT NULL,
                      description VARCHAR(600) NOT NULL,
                      duration INTEGER NOT NULL,
                      releaseDate TIMESTAMP NOT NULL,
                      rating_id  INT REFERENCES Rating(id));


CREATE TABLE Genre_film (
                            film_id INT REFERENCES Film(id),
                            rating_id INT REFERENCES Rating(id));

CREATE TABLE Genre (
                       id serial PRIMARY KEY,
                       name VARCHAR(15) NOT NULL);

CREATE TABLE Users (
                      id serial PRIMARY KEY,
                      email VARCHAR(45) NOT NULL,
                      login VARCHAR(45) NOT NULL,
                      name VARCHAR(45) NOT NULL,
                      birthday TIMESTAMP NOT NULL);

CREATE TABLE Likes (
                      film_id INT REFERENCES Film(id),
                      users_id INT REFERENCES Users(id));


CREATE TABLE Friend (
                        from_requested_id INT REFERENCES Users(id),
                        to_request_id INT REFERENCES Users(id),
                        is_confirmed BOOLEAN NOT NULL);



