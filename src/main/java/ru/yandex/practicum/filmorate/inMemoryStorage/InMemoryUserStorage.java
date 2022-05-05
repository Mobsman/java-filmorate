package ru.yandex.practicum.filmorate.inMemoryStorage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.ValidationUser;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User create(User user) throws ValidationException {
        if (ValidationUser.validate(user)) {
            users.put(user.getId(), user);
            log.info("Добавлен user: {}", user);
            return user;
        }
        return null;
    }

    @Override
    public User update(User user) throws ValidationException {
        if (ValidationUser.validate(user)) {
            users.put(user.getId(), user);
            log.info("Добавлен film: {}", user);
            return user;
        }
        return null;
    }

    @Override
    public void remove(Long id)  {
        for(Iterator<Map.Entry<Long, User>> it = users.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, User> entry = it.next();
            if(entry.getKey().equals(id)) {
                it.remove();
            }
            log.info("Не найден user: {}", id);
        }
        log.info("Удален user: {}", id);
    }

    @Override
    public User getById(Long id) {
        User user = new User();
        for(Iterator<Map.Entry<Long, User>> it = users.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, User> entry = it.next();
            if(entry.getKey().equals(id)) {
                user = entry.getValue();
            } else user=null;
            log.info("Не найден user: {}", id);
        }
        log.info("Получен user: {}", id);
        return user;
    }

    @Override
    public Collection<User> getAll() {
        log.info("Получен список пользователей");
        return users.values();
    }
}
