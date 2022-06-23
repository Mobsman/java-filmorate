package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.RatingNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.utils.Converter.ConverterFilmToFilmDto;
import ru.yandex.practicum.filmorate.utils.FilmValidator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final ConverterFilmToFilmDto converter;

    public FilmDto create(Film film) throws ValidationException {
        return converter.convert(filmStorage.create(film));
    }

    public FilmDto update(Film film) throws ValidationException, FilmNotFoundException {

        Film film1 = null;
        film1 = filmStorage.update(film);


        FilmDto film2 = converter.
                convert(film1);

        return film2;
    }

    public void remove(Long id) {
        filmStorage.remove(id);
    }


    public FilmDto getById(Long id) throws FilmNotFoundException {
        return converter.convert(filmStorage.getById(id));
    }


    public Collection<FilmDto> getAll() {
        return filmStorage.getAll().stream().map(converter::convert).collect(Collectors.toList());
    }

    public void addLike(Long filmId, Long userId) {

        filmStorage.addLike(filmId, userId);

    }

    public void deleteLike(Long filmId, Long userId) throws FilmNotFoundException {

        Film film = filmStorage.getById(filmId);
        film.getLikes().remove(userId);

    }

    public List<Film> getPopularFilm(int countFilm) {

        return filmStorage.getPopularFilm(countFilm);
    }

    public List<Rating> getAllRatings() {
        return filmStorage.getAllRatings();
    }

    public Rating getRatingById(int ratingId) throws RatingNotFoundException, GenreNotFoundException {
        return filmStorage.getRatingById(ratingId);
    }

    public List<Genre> getAllGenre() {
        return filmStorage.getAllGenres();
    }

    public Genre getGenreById(int ratingId) throws GenreNotFoundException {

        return filmStorage.getGenreById(ratingId);

    }
}
