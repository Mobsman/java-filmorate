package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.annotation.CheckName;
import ru.yandex.practicum.filmorate.utils.annotation.DateOfBirth;
import ru.yandex.practicum.filmorate.utils.annotation.Email;
import ru.yandex.practicum.filmorate.utils.annotation.NotEmpty;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUser {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public static boolean validateEmail(final String hex) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }


    public static boolean validate(Object obj) throws IllegalAccessException {

        LocalDate d = LocalDate.now();

        Class clacc = obj.getClass();
        Field[] field = clacc.getDeclaredFields();

        for (Field fields : field) {
            fields.setAccessible(true);

            if (fields.isAnnotationPresent(DateOfBirth.class)) {
                if (((LocalDate) fields.get(obj)).isAfter(d)) {
                    throw new ValidationException("Неверная дата рождения");
                }
            }
            if (fields.isAnnotationPresent(CheckName.class)) {
                if (((String) fields.get(obj)).isBlank()) {
                    ((User) obj).setName(((User) obj).getLogin());
                }
            }
            if (fields.isAnnotationPresent(Email.class)) {
                if (!(validateEmail(((User) obj).getEmail()))) {
                    throw new ValidationException("Неправильная почта");
                }
            }
            if (fields.isAnnotationPresent(NotEmpty.class)) {
                if (((String) fields.get(obj)).isBlank()) {
                    throw new ValidationException("Отсутствует название фильма");
                }
            }
        }
        return true;
    }
}

