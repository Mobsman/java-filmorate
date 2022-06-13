package ru.yandex.practicum.filmorate.service;




import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserStorage userStorage;

    public User create(User user) throws ValidationException {
       return userStorage.create(user);
    }


    public User update(User user) throws ValidationException {
       return userStorage.update(user);
    }


    public void remove(Long id) {
      userStorage.remove(id);
    }


    public User getById(Long id) {
      return userStorage.getById(id);
    }


    public Collection<User> getAll() {
      return userStorage.getAll();
    }

    public void addFriend(Long userId, Long friendId) {

        User user = getById(userId);
        User friend = getById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFriend(Long userId, Long friendId) {

        User user = getById(userId);
        user.getFriends().remove(friendId);
    }


    public Object[] getAllFriends(Long userId) {
        User user = getById(userId);
        return user.getFriends().toArray();
    }

    public List<Long> getAllCommonFriends(Long userId, Long friendId) {

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