package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.utils.annotation.Duration;
import ru.yandex.practicum.filmorate.utils.annotation.Length;
import ru.yandex.practicum.filmorate.utils.annotation.NotEmpty;
import ru.yandex.practicum.filmorate.utils.annotation.ReleaseDate;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class ValidationFilm {




    public static boolean validate(Object obj) throws IllegalAccessException {

        LocalDate d = LocalDate.of(1895, 12, 28);
        Integer i = 0;

        Class clacc = obj.getClass();
        Field[] field = clacc.getDeclaredFields();

        for (Field fields : field) {
            fields.setAccessible(true);

            if (fields.isAnnotationPresent(NotEmpty.class)) {
                if (((String) fields.get(obj)).isBlank()) {
                    throw new ValidationException("Отсутствует название фильма");
                }
            }
            if (fields.isAnnotationPresent(Length.class)) {
                if (((String) fields.get(obj)).length() > 200) {
                    throw new ValidationException("Описание больше 200 символов");
                }
            }
            if (fields.isAnnotationPresent(Duration.class)) {
                if (((Integer) fields.get(obj)).compareTo(i) == 0 || ((Integer) fields.get(obj)).compareTo(i) < 0) {
                    throw new ValidationException("Продолжительность меньше или равна нулю");
                }
            }
            if (fields.isAnnotationPresent(ReleaseDate.class)) {
                if (((LocalDate) fields.get(obj)).isBefore(d)) {
                    throw new ValidationException("Неверная дата релиза");
                }
            }
        }
        return true;
    }
}

