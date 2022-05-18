package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.utils.FilmValidator;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

   private final FilmStorage filmStorage;

    public Film create(Film film) throws ValidationException {
            return filmStorage.create(film);
    }

    public Film update(Film film) throws ValidationException {
            return filmStorage.update(film);
    }

    public void remove(Long id) {
      filmStorage.remove(id);
    }


    public Film getById(Long id) {
      return filmStorage.getById(id);
    }


    public Collection<Film> getAll() {
      return filmStorage.getAll();
    }

    public void addLike(Long filmId, Long userId) {

        Film film = getById(filmId);
        film.getLikes().add(userId);

    }

    public void deleteLike(Long filmId, Long userId) {

        Film film = getById(filmId);
        film.getLikes().remove(userId);

    }

    public List<Film> getPopularFilm(int countFilm) {

        List<Film> films = new ArrayList<>(getAll());
        Collections.sort(films);

        List<Film> filmsSorted = new ArrayList<>(films);

        if (filmsSorted.size() > countFilm ) {
            return filmsSorted.subList(0, countFilm);
        } else if (countFilm > filmsSorted.size()){
            return filmsSorted;
        }

        return filmsSorted.subList(0, 10);
    }


}
