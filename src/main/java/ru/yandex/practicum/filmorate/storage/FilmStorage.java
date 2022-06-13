package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;

@Primary
public interface FilmStorage {

    Film create(Film film) throws ValidationException;

    Film update(Film film) throws ValidationException;

    void remove(Long id);

    Film getById(Long id);

    Collection<Film> getAll();

}
