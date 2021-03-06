package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.remove(id);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
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

