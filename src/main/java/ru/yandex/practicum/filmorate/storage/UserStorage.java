package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.Collection;



public interface UserStorage {

    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

    void remove(Long id);

    User getById(Long id);

    Collection<User> getAll();

    void addFriend(Long userId, Long friendId);

    void removeFriend(Long userId, Long friendId);

    Collection<User> getAllFriends(Long userId);




}
