package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
public class FilmService {


    FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    public void addLike(Long filmId, Long userId) {

        Film film = filmStorage.getById(filmId);
        film.getLikes().add(userId);

    }

    public void deleteLike(Long filmId, Long userId) {

        Film film = filmStorage.getById(filmId);
        film.getLikes().remove(userId);

    }

    public List<Film> getPopularFilm(int countFilm) {

        List<Film> films = new ArrayList<>(getFilmStorage().getAll());
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
