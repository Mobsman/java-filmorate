package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.ValidationFilm;

import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping()
    public Collection<Film> getAll() {
        log.info("Получен список фильмов");
        return films.values();

    }

    @PostMapping()
    public Film post(@RequestBody Film film) throws IllegalAccessException {
        if (ValidationFilm.validate(film)){
            films.put(film.getId(), film);
            log.info("Добавлен film: {}", film);
            return film;
        }
        return null;
    }

    @PutMapping()
    public Film put(@RequestBody Film film) throws IllegalAccessException {
        if (ValidationFilm.validate(film)){
            films.put(film.getId(), film);
            log.info("Перезаписан film: {}", film);
            return film;
        }
        return null;
    }


}
