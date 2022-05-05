package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class UserService {


    void addFriend(User user, Long friendId) {
        user.getFriends().add(friendId);
    }


}