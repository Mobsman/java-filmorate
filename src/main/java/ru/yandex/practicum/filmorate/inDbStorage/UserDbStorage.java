package ru.yandex.practicum.filmorate.inDbStorage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Component("userDbStorage")
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) throws ValidationException {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp timestamp = Timestamp.valueOf(user.getBirthday().atStartOfDay());

            jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement userStatement = con.prepareStatement(
                        "INSERT INTO USERS " + "(EMAIL,LOGIN,NAME,BIRTHDAY) VALUES (?,?,?,?)", 1);
                userStatement.setString(1, user.getEmail());
                userStatement.setString(2, user.getLogin());
                userStatement.setString(3, user.getName());
                userStatement.setTimestamp(4, timestamp);
                ResultSet rs = userStatement.getGeneratedKeys();
                return userStatement;
            }
        }, keyHolder);

        return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User userdb = new User();
                userdb.setId((long) rs.getInt(1));
                userdb.setEmail(rs.getString(2));
                userdb.setLogin(rs.getString(3));
                userdb.setName(rs.getString(4));
                userdb.setBirthday(rs.getTimestamp(5).toLocalDateTime().toLocalDate());
                return userdb;
            }
        }, keyHolder.getKey());
    }

    @Override
    public User update(User user) throws ValidationException {

        Timestamp timestamp = Timestamp.valueOf(user.getBirthday().atStartOfDay());

        String sql = "UPDATE users SET email = ?, login = ?,name = ?,birthday = ? WHERE id = ? ";
        int rowNum = jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), timestamp, user.getId());

        if (rowNum == 1) {
            return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?", new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User userdb = new User();
                    userdb.setId((long) rs.getInt(1));
                    userdb.setEmail(rs.getString(2));
                    userdb.setLogin(rs.getString(3));
                    userdb.setName(rs.getString(4));
                    userdb.setBirthday(rs.getTimestamp(5).toLocalDateTime().toLocalDate());
                    return userdb;
                }
            }, user.getId());
        }
        return null;
    }

    @Override
    public void remove(Long id) {

        String sql = "DELETE FROM users WHERE id = ? ";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public User getById(Long id) {

        return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User userdb = new User();
                userdb.setId((long) rs.getInt(1));
                userdb.setEmail(rs.getString(2));
                userdb.setLogin(rs.getString(3));
                userdb.setName(rs.getString(4));
                userdb.setBirthday(rs.getTimestamp(5).toLocalDateTime().toLocalDate());
                return userdb;
            }
        }, id);
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


    public void addFriend(Long userId, Long friendId) {

        User user = getById(userId);
        User friend = getById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }
}
