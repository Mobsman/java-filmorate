package ru.yandex.practicum.filmorate.inDbStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.UserValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component("UserDbStorage")
@Primary
public class UserDbStorage implements UserStorage {


    private final JdbcTemplate jdbcTemplate;


    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) throws ValidationException, UserNotFoundException {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp timestamp = Timestamp.valueOf(user.getBirthday().atStartOfDay());

        if (UserValidator.validate(user)) {
            jdbcTemplate.update(con -> {
                PreparedStatement userStatement = con.prepareStatement(
                        "INSERT INTO USERS " + "(EMAIL,LOGIN,NAME,BIRTHDAY) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                userStatement.setString(1, user.getEmail());
                userStatement.setString(2, user.getLogin());
                userStatement.setString(3, user.getName());
                userStatement.setTimestamp(4, timestamp);
                return userStatement;
            }, keyHolder);
        }

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?", this::userRows, keyHolder.getKey());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        throw new UserNotFoundException("пользователь ненайден");
    }

    @Override
    public User update(User user) throws ValidationException, UserNotFoundException {

        Timestamp timestamp = Timestamp.valueOf(user.getBirthday().atStartOfDay());

        if (UserValidator.validate(user)) {
            String sql = "UPDATE users SET email = ?, login = ?,name = ?,birthday = ? WHERE id = ? ";
            int rowNum = jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), timestamp, user.getId());


            try {
                return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?", this::userRows, user.getId());
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
        throw new UserNotFoundException("пользователь ненайден");
    }


    @Override
    public void remove(Long id) {

        String sql = "DELETE FROM users WHERE id = ? ";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?", this::userRows, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        throw new UserNotFoundException("Пользователь не найден");
    }

    @Override
    public Collection<User> getAll() {

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT * FROM USERS");

        List<User> users = new ArrayList<>();

        for (Map<String, Object> map : mapList) {
            User user = new User();
            long id = ((Number) map.get("id")).longValue();
            user.setId((id));
            user.setEmail((String) map.get("email"));
            user.setLogin((String) map.get("login"));
            user.setName((String) map.get("name"));
            user.setBirthday(((Timestamp) map.get("birthday")).toLocalDateTime().toLocalDate());
            users.add(user);
        }
        return users;
    }

    public void addFriend(Long userId, Long friendId) throws UserNotFoundException {

        User user = getById(userId);
        User friend = getById(friendId);

        String sql = "INSERT INTO friend (user_id,friend_id) VALUES (?,?)";

        if (user != null && friend != null) {
            if (chekDuplicateFriend(userId, friendId).size() <= 0) {
                jdbcTemplate.update(sql, userId, friendId);
            }
        }
    }

    private List<Map<String, Object>> chekDuplicateFriend(Long userId, Long friendId) {
        String sql = "SELECT user_id,friend_id FROM Friend" +
                " WHERE (user_id=? AND friend_id=? )";
        return jdbcTemplate.queryForList(sql, userId, friendId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        String sql = "DELETE FROM friend WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public Collection<User> getAllFriends(Long userId) {

        String sql = "SELECT * FROM users WHERE id IN (SELECT friend_id FROM friend WHERE user_id = ?)";

        return jdbcTemplate.query(sql, this::userRows, userId);
    }


    private User userRows(ResultSet rowSet, int rowNum) throws SQLException {
        return User.builder()
                .id((long) rowSet.getInt("id"))
                .login(rowSet.getString("login"))
                .name(rowSet.getString("name"))
                .email(rowSet.getString("email"))
                .birthday(rowSet.getDate("birthday").toLocalDate())
                .friends(getAllFriends((long) rowSet.getInt("id")).stream().map(User::getId).collect(Collectors.toSet()))
                .build();
    }


}

