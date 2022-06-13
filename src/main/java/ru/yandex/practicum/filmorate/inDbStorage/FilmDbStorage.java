package ru.yandex.practicum.filmorate.inDbStorage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@AllArgsConstructor
@Component("filmDbStorage")

public class FilmDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) throws ValidationException {
        return null;
    }

    @Override
    public User update(User user) throws ValidationException {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }
}
