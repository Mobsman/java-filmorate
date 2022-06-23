package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.RatingNotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/mpa")
public class RatingController {

    private final FilmService filmService;

    public RatingController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Rating> getAllRatings() {
        List<Rating> ratings = new ArrayList<>(filmService.getAllRatings());
        log.info("всего рейтингов в базе: {}", ratings.size());
        return ratings;
    }

    @GetMapping("/{ratingId}")
    public Rating getRatingById(@PathVariable Integer ratingId) throws RatingNotFoundException, GenreNotFoundException {
        log.info("запрашиваемы рейтинг : " + ratingId);
        return filmService.getRatingById(ratingId);
    }
}
