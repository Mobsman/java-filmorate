package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

   private final FilmService filmService;

    @GetMapping()
    public Collection<Film> getAll() {
        return filmService.getFilmStorage().getAll();
    }

    @PostMapping()
    public Film post(@RequestBody Film film) throws ValidationException {
        return filmService.getFilmStorage().create(film);
        // Я здесь вроде вызываю метод getFilmStorage() из сервиса , не понимаю как еще можно обратится к FilmStorage
    }

    @PutMapping()
    public Film put(@RequestBody Film film) throws ValidationException {
        return filmService.getFilmStorage().update(film);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id)  {
        filmService.getFilmStorage().remove(id);
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Long id) {
        return filmService.getFilmStorage().getById(id);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilm(count);
    }



}
