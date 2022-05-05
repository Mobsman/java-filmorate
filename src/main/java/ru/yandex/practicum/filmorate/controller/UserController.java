package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.inMemoryStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    InMemoryUserStorage userService = new InMemoryUserStorage();

    @GetMapping()
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @PostMapping()
    public User post(@RequestBody User user) throws ValidationException {
        return userService.create(user);
    }

    @PutMapping()
    public User put(@RequestBody User user) throws ValidationException {
        return userService.update(user);
    }

    @DeleteMapping ("/{id}")
    public void delete(@PathVariable Long id)  {
        userService.remove(id);
    }

    @GetMapping ("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }
}

