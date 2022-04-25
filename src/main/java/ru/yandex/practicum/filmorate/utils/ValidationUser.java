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


    public static boolean validate(User user) throws ValidationException {

        LocalDate d = LocalDate.now();

        Class clacc = user.getClass();
        Field[] field = clacc.getDeclaredFields();

        for (Field fields : field) {
            fields.setAccessible(true);

            if (fields.isAnnotationPresent(DateOfBirth.class)) {
                if ((user.getBirthday().isAfter(d))) {
                    throw new ValidationException(" возникла ошибка ");
                }
            }
            if (fields.isAnnotationPresent(CheckName.class)) {
                if ((user.getName().isBlank())) {
                    user.setName(user.getLogin());
                }
            }
            if (fields.isAnnotationPresent(Email.class)) {
                if (!(validateEmail(user.getEmail()))) {
                    throw new ValidationException(" возникла ошибка ");
                }
            }
            if (fields.isAnnotationPresent(NotEmpty.class)) {
                if ((user.getLogin().isBlank())) {
                    throw new ValidationException(" возникла ошибка ");
                }
            }
        }
        return true;
    }
}

