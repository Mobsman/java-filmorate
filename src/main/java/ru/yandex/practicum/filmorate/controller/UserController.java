package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.ValidationUser;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new ConcurrentHashMap<>();


    @GetMapping()
    public Collection<User> getAll() {
        log.info("Получен список пользователей");
        return users.values();
    }

    @PostMapping()
    public User post(@RequestBody User user) throws ValidationException {
        if (ValidationUser.validate(user)) {
            users.put(user.getId(), user);
            log.info("Создан новый пользователь: {}", user);
            return user;
        }
        return null;
    }

    @PutMapping()
    public User put(@RequestBody User user) throws ValidationException {
        if (ValidationUser.validate(user)) {
            users.put(user.getId(), user);
            log.info("Обновленны данные пользователя: {}", user);
            return user;
        }
        return null;
    }
}

