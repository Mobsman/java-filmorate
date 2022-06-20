package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.Converter.ConverterUserToUserDto;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final ConverterUserToUserDto converter;

    public UserDto create(User user) throws ValidationException {
        return converter.convert(userStorage.create(user));
    }

    public UserDto update(User user) throws ValidationException {

        return converter.convert(userStorage.update(user));
    }

    public void remove(Long id) {
        userStorage.remove(id);
    }


    public UserDto getById(Long id) {
        return converter.convert(userStorage.getById(id));
    }

    public Collection<UserDto> getAll() {

        return userStorage.getAll().stream().map(converter::convert).collect(Collectors.toList());
    }

    public void addFriend(Long userId, Long friendId) {

        userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {

        userStorage.removeFriend(userId, friendId);
    }

    public Collection<UserDto> getAllFriends(Long userId) {

        return userStorage.getAllFriends(userId).stream().map(converter::convert).collect(Collectors.toList());
    }

    public List<UserDto> getAllCommonFriends(Long userId, Long friendId) {

        List<User> commonFriends = new ArrayList<>();

        Collection<User> userFriends = userStorage.getAllFriends(userId);
        Collection<User> friends = userStorage.getAllFriends(friendId);

        for (User u : userFriends) {
            for (User f : friends) {
                if (u.equals(f)) {
                    commonFriends.add(f);
                }
            }
        }
        return commonFriends.stream().map(converter::convert).collect(Collectors.toList());
    }
}