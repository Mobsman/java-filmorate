package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.ValidationFilm;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new ConcurrentHashMap<>();

    @GetMapping()
    public Collection<Film> getAll() {
        log.info("Получен список фильмов");
        return films.values();

    }

    @PostMapping()
    public Film post(@RequestBody Film film) throws ValidationException {
        if (ValidationFilm.validate(film)){
            films.put(film.getId(), film);
            log.info("Добавлен film: {}", film);
            return film;
        }
        return null;
    }

    @PutMapping()
    public Film put(@RequestBody Film film) throws ValidationException {
        if (ValidationFilm.validate(film)){
            films.put(film.getId(), film);
            log.info("Перезаписан film: {}", film);
            return film;
        }
        return null;
    }


}
