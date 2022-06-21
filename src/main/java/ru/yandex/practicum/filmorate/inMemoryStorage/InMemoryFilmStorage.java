package ru.yandex.practicum.filmorate.inMemoryStorage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.FilmValidator;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new ConcurrentHashMap<>();

    @Override
    public Film create(Film film) throws ValidationException {
        if (FilmValidator.validate(film)) {
            films.put(film.getId(), film);
            log.info("Добавлен film: {}", film);
            return film;
        }
        return null;
    }

    @Override
    public Film update(Film film) throws ValidationException {
        if (FilmValidator.validate(film)) {
            films.put(film.getId(), film);
            log.info("Добавлен film: {}", film);
            return film;
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        for (Iterator<Map.Entry<Long, Film>> it = films.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, Film> entry = it.next();
            if (entry.getKey().equals(id)) {
                it.remove();
            }
            log.info("Не найден film: {}", id);
        }
        log.info("Удален film: {}", id);
    }

    @Override
    public Film getById(Long id) {
        Film film = new Film();
        for (Iterator<Map.Entry<Long, Film>> it = films.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, Film> entry = it.next();
            if (entry.getKey().equals(id)) {
                film = entry.getValue();
            }
        }

        if (film == null) {
            log.info("Не найден film: {}", id);
            return null;
        }

        log.info("Получен film: {}", id);
        return film;
    }

    @Override
    public Collection<Film> getAll() {
        log.info("Получен список фильмов");
        return films.values();
    }

    @Override
    public void addLike(Long filmId, Long userId) {

    }

    @Override
    public void deleteLike(Long filmId, Long userId) {

    }

    @Override
    public List<Film> getPopularFilm(Integer countFilm) {
        return null;
    }

    @Override
    public List<Rating> getAllRatings() {
        return null;
    }

    @Override
    public Rating getRatingById(int ratingId) {
        return null;
    }

    @Override
    public List<Genre> getAllGenres() {
        return null;
    }

    @Override
    public Genre getGenreById(Integer genreId) {
        return null;
    }
}
