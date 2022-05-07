package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.Collection;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public Collection<User> getAll() {
        return userService.getUserStorage().getAll();
    }

    @PostMapping()
    public User post(@RequestBody User user) throws ValidationException {
        return userService.getUserStorage().create(user);
    }

    @PutMapping()
    public User put(@RequestBody User user) throws ValidationException {
        return userService.getUserStorage().update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.getUserStorage().remove(id);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getUserStorage().getById(id);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {

        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {

        userService.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public Object[] getFriendsById(@PathVariable Long userId) {
        return userService.getAllFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<Long> getAllCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        return userService.getAllCommonFriends(userId,otherId);
    }
}

