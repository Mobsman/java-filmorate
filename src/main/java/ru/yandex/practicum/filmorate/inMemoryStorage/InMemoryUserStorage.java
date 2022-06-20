package ru.yandex.practicum.filmorate.inMemoryStorage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.UserValidator;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User create(User user) throws ValidationException {
        if (UserValidator.validate(user)) {
            users.put(user.getId(), user);
            log.info("Добавлен user: {}", user);
            return user;
        }
        return null;
    }

    @Override
    public User update(User user) throws ValidationException {
        if (UserValidator.validate(user)) {
            users.put(user.getId(), user);
            log.info("Добавлен film: {}", user);
            return user;
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        for (Iterator<Map.Entry<Long, User>> it = users.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, User> entry = it.next();
            if (entry.getKey().equals(id)) {
                it.remove();
            }
            log.info("Не найден user: {}", id);
        }
        log.info("Удален user: {}", id);
    }

    @Override
    public User getById(Long id) {
        User user = new User();
        for (Iterator<Map.Entry<Long, User>> it = users.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, User> entry = it.next();
            if (entry.getKey().equals(id)) {
                user = entry.getValue();
            }

        }
        if (user==null){
            log.info("Не найден user: {}", id);
            return null;
        }

        log.info("Получен user: {}", id);
        return user;
    }

    @Override
    public Collection<User> getAll() {
        log.info("Получен список пользователей");
        return users.values();
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        User user = getById(userId);
        User friend = getById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {

    }

    @Override
    public Collection<User> getAllFriends(Long userId) {
        return null;
    }

    public Collection<Long> getAllCommonFriends(Long userId, Long friendId) {
        List<Long> commonFriends = new ArrayList<>();

        User user = getById(userId);
        User friend = getById(friendId);

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
