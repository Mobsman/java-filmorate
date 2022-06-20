package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public FilmDto update(Film film) throws ValidationException {

        return converter.convert(filmStorage.update(film));
    }

    public void remove(Long id) {
        filmStorage.remove(id);
    }


    public Film getById(Long id) {
        return filmStorage.getById(id);
    }


    public Collection<FilmDto> getAll() {
        return filmStorage.getAll().stream().map(converter::convert).collect(Collectors.toList());
    }

    public void addLike(Long filmId, Long userId) {

        filmStorage.addLike(filmId,userId);

    }

    public void deleteLike(Long filmId, Long userId) {

        Film film = getById(filmId);
        film.getLikes().remove(userId);

    }

    public List<Film> getPopularFilm(int countFilm) {

        return filmStorage.getPopularFilm(countFilm);
    }

    public List<Rating> getAllRatings() {
        return filmStorage.getAllRatings();
    }

    public Rating getRatingById(int ratingId) {
        return filmStorage.getRatingById(ratingId);
    }

    public List<Genre> getAllGenre() {
        return filmStorage.getAllGenres();
    }

    public Genre getGenreById(int ratingId) {
        return filmStorage.getGenreById(ratingId);
    }

}
