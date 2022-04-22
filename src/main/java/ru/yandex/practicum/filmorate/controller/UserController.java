package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.ValidationUser;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping()
    public Collection<User> getAll() {
        log.info("Получен список пользователей");
        return users.values();
    }

    @PostMapping()
    public User post(@RequestBody User user) throws IllegalAccessException {
        if (ValidationUser.validate(user)) {
            users.put(user.getId(), user);
            log.info("Создан новый пользователь: {}", user);
            return user;
        }
        return null;
    }

    @PutMapping()
    public User put(@RequestBody User user) throws IllegalAccessException {
        if (ValidationUser.validate(user)) {
            users.put(user.getId(), user);
            log.info("Обновленны данные пользователя: {}", user);
            return user;
        }
        return null;
    }
}

