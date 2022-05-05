package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.inMemoryStorage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    InMemoryFilmStorage filmService = new InMemoryFilmStorage();

    @GetMapping()
    public Collection<Film> getAll() {
        return filmService.getAll();
    }

    @PostMapping()
    public Film post(@RequestBody Film film) throws ValidationException {
        return filmService.create(film);
    }

    @PutMapping()
    public Film put(@RequestBody Film film) throws ValidationException {
        return filmService.update(film);
    }

    @DeleteMapping ("/{id}")
    public void delete(@PathVariable Long id) throws ValidationException {
        filmService.remove(id);
    }

    @GetMapping ("/{id}")
    public Film getById(@PathVariable Long id) {
        return filmService.getById(id);
    }

}
