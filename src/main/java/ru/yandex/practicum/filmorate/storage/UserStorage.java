package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;



public interface UserStorage {

    User create(User user) throws ValidationException, UserNotFoundException;

    User update(User user) throws ValidationException, UserNotFoundException;

    void remove(Long id);

    User getById(Long id) throws UserNotFoundException;

    Collection<User> getAll();

    void addFriend(Long userId, Long friendId) throws UserNotFoundException;

    void removeFriend(Long userId, Long friendId);

    Collection<User> getAllFriends(Long userId);




}
