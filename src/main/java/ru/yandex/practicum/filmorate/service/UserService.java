package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class UserService {

    UserStorage userStorage;


    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public UserStorage getUserStorage() {
        return userStorage;
    }

    public void addFriend(Long userId, Long friendId) {

        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFriend(Long userId, Long friendId) {

        User user = userStorage.getById(userId);
        user.getFriends().remove(friendId);
    }


    public Object[] getAllFriends(Long userId) {
        User user = userStorage.getById(userId);
        return user.getFriends().toArray();
    }

    public List<Long> getAllCommonFriends(Long userId, Long friendId) {

        List<Long> commonFriends = new ArrayList<>();

        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        Set<Long> userFriends = user.getFriends();
        Set <Long> friends = friend.getFriends();

        for (Long u : userFriends) {
            for (Long f : friends) {
                if(u.equals(f)){
                    commonFriends.add(f);
                }
            }
        }
        return commonFriends;
    }
}