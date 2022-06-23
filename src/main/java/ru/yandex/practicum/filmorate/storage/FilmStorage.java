package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.RatingNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;
import java.util.List;


public interface FilmStorage {

    Film create(Film film) throws ValidationException;

    Film update(Film film) throws ValidationException, FilmNotFoundException;

    void remove(Long id);

    Film getById(Long id) throws FilmNotFoundException;

    Collection<Film> getAll();

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Film> getPopularFilm(Integer countFilm);

    List<Rating> getAllRatings();

    Rating getRatingById(int ratingId) throws GenreNotFoundException, RatingNotFoundException;

    List<Genre> getAllGenres();

    Genre getGenreById(Integer genreId) throws GenreNotFoundException;
}
