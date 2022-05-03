package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.annotation.Duration;
import ru.yandex.practicum.filmorate.utils.annotation.Length;
import ru.yandex.practicum.filmorate.utils.annotation.NotEmpty;
import ru.yandex.practicum.filmorate.utils.annotation.ReleaseDate;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class ValidationFilm {




    public static boolean validate(Film film) throws ValidationException {

        LocalDate d = LocalDate.of(1895, 12, 28);
        Integer i = 0;

        Class clacc = film.getClass();
        Field[] field = clacc.getDeclaredFields();

        for (Field fields : field) {
            fields.setAccessible(true);

            if (fields.isAnnotationPresent(NotEmpty.class)) {
                if ((film.getName().isBlank())) {
                    throw  new ValidationException(" возникла ошибка ");
                }
            }
            if (fields.isAnnotationPresent(Length.class)) {
                if (film.getDescription().length() > 200) {
                    throw  new ValidationException(" возникла ошибка ");
                }
            }
            if (fields.isAnnotationPresent(Duration.class)) {
                if (film.getDuration().compareTo(i) == 0 || (film.getDuration().compareTo(i) < 0)) {
                    throw  new ValidationException(" возникла ошибка ");
                }
            }
            if (fields.isAnnotationPresent(ReleaseDate.class)) {
                if (film.getReleaseDate().isBefore(d)) {
                    throw  new ValidationException(" возникла ошибка ");
                }
            }
        }
        return true;
    }
}

