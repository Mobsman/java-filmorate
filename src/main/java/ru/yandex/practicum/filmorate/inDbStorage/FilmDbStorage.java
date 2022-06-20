package ru.yandex.practicum.filmorate.inDbStorage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.utils.FilmValidator;


import java.sql.*;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Component
@Primary
public class FilmDbStorage implements FilmStorage {


    private final JdbcTemplate jdbcTemplate;

    private final String getFilm = "SELECT f.id,f.name,f.description,f.duration,f.release_date,f.rating_id, " +
            "r.name FROM FILM f JOIN Rating r ON f.rating_id = r.id where f.id=?";


    @Override
    public Film create(Film film) throws ValidationException {

        FilmValidator.validate(film);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp timestamp = Timestamp.valueOf(film.getReleaseDate().atStartOfDay());

        jdbcTemplate.update(con -> {
            PreparedStatement filmStatement = con.prepareStatement(
                    "INSERT INTO FILM " + "(NAME,DESCRIPTION,DURATION,RELEASE_DATE,RATING_ID) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            filmStatement.setString(1, film.getName());
            filmStatement.setString(2, film.getDescription());
            filmStatement.setInt(3, film.getDuration());
            filmStatement.setTimestamp(4, timestamp);
            filmStatement.setInt(5, film.getMpa().getId());
            return filmStatement;
        }, keyHolder);

        addGenre(film);

        return jdbcTemplate.queryForObject(getFilm, this::filmRows, keyHolder.getKey());

    }

    @Override
    public Film update(Film film) throws ValidationException {

        Timestamp timestamp = Timestamp.valueOf(film.getReleaseDate().atStartOfDay());
        FilmValidator.validate(film);

        String sql = "UPDATE film SET NAME=?,DESCRIPTION=?,DURATION=?,RELEASE_DATE=?,RATING_ID=? WHERE id = ?";
        int rowNum = jdbcTemplate.update(sql, film.getName(), film.getDescription(),
                film.getDuration(), timestamp, film.getMpa().getId(), film.getId());


        if (rowNum == 1) {
            addGenre(film);
            return jdbcTemplate.queryForObject(getFilm, this::filmRows, film.getId());
        }

        throw new FilmNotFoundException("фильм ненайден");
    }

    public void addGenre(Film film) {

        String sqlDelete = "DELETE FROM GENRE_FILM WHERE FILM_ID = ?";

            jdbcTemplate.update(sqlDelete, film.getId());
            if (film.getGenres() == null || film.getGenres().isEmpty()) {
                return;
            }
            Set<Genre> filmGenre = film.getGenres();
            for (Genre genre : filmGenre) {
                jdbcTemplate.update(con -> {
                    PreparedStatement genreStatement = con.prepareStatement(
                            "INSERT INTO GENRE_FILM " + "(FILM_ID, GENRE_ID) VALUES (?,?)");
                    genreStatement.setInt(1, Math.toIntExact(film.getId()));
                    genreStatement.setInt(2, genre.getId());
                    return genreStatement;
                });
            }
}

    @Override
    public void remove(Long id) {
        String sql = "DELETE FROM FILM WHERE id = ? ";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Film getById(Long id) {

        try {
            return jdbcTemplate.queryForObject(getFilm, this::filmRows, id);
        } catch (DataAccessException e) {
            throw new FilmNotFoundException("фильм ненайден");
        }

    }

    @Override
    public Collection<Film> getAll() {

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT" +
                " f.id,f.name,f.description,f.duration,f.release_date,f.rating_id, r.name as rating_name\n" +
                "FROM FILM f inner JOIN Rating r ON f.rating_id = r.id ");

        List<Film> films = new ArrayList<>();

        for (Map<String, Object> map : mapList) {

            long idGenre = ((Number) map.get("id")).longValue();

            Film film = new Film();
            long id = ((Number) map.get("id")).longValue();
            film.setId((id));
            film.setName((String) map.get("name"));
            film.setDescription((String) map.get("description"));
            film.setDuration((Integer) map.get("duration"));
            film.setReleaseDate(((Timestamp) map.get("release_date")).toLocalDateTime().toLocalDate());
            film.setMpa(Rating.builder()
                    .id((Integer) map.get("rating_id"))
                    .name((String) map.get("rating_name")).build());
            film.setGenres(getGenreFilm(idGenre));
            film.setLikes(getLikes(id));
            films.add(film);
        }
        return films;
    }

    @Override
    public void addLike(Long filmId, Long userId) {

        String sql = "INSERT INTO LIKES SET FILM_ID = ?, USERS_ID = ?";

        if (chekDuplicateLike(filmId, userId).size() <= 0) {
            jdbcTemplate.update(sql, filmId, userId);
        }

    }

    private List<Map<String, Object>> chekDuplicateLike(Long filmId, Long userId) {

        String sql = "SELECT film_id,users_id FROM likes" +
                " WHERE (film_id=? AND users_id=? )";
        return jdbcTemplate.queryForList(sql, filmId, userId);
    }

    private Set<Genre> getGenreFilm(Long filmId) {

        String sql = "select g.id,g.name from film f inner join genre_film gf " +
                "on f.id = ? inner join genre g on g.id = gf.GENRE_ID";

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, filmId);

        Set<Genre> genres = new HashSet<>();

        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                genres.add(Genre.builder().
                        id((Integer) map.get("id"))
                        .name((String) map.get("name"))
                        .build());
            }
        }
        return genres;
    }


    private Set<Long> getLikes(Long filmId) {

        String sql = "SELECT USERS_ID FROM LIKES WHERE Film_id = ?";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, filmId);

        Set<Long> likes = new HashSet<>();

        for (Map<String, Object> map : mapList) {
            long id = ((Number) map.get("users_id")).longValue();
            User user = new User();
            user.setId(id);
            likes.add(user.getId());
        }
        return likes;
    }


    public void deleteLike(Long filmId, Long userId) {

        String sql = "DELETE FROM LIKES WHERE film_id = ? and users_id = ? ";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getPopularFilm(int countFilm) {

        List<Film> film = (List<Film>) getAll();
        Collections.sort(film);
        return film.subList(0, countFilm);

    }

    @Override
    public List<Rating> getAllRatings() {

        String sql = "SELECT * FROM RATING";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Rating.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build());
    }

    @Override
    public Rating getRatingById(int ratingId) {
        String sql = "SELECT * FROM RATING WHERE ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> Rating.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .build(), ratingId);
        } catch (DataAccessException e) {
            throw new GenreNotFoundException("рейтинг не найден");
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM GENRE ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Genre.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build());
    }

    @Override
    public Genre getGenreById(Integer genreId) {

        String sql = "SELECT * FROM GENRE WHERE ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> Genre.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .build(), genreId);
        } catch (DataAccessException e) {
            throw new GenreNotFoundException("жанр не найден");
        }
    }

    private Film filmRows(ResultSet rowSet, int rowNum) throws SQLException {

        return Film.builder()
                .id((long) rowSet.getInt("id"))
                .name(rowSet.getString("name"))
                .releaseDate(rowSet.getDate("release_date").toLocalDate())
                .description(rowSet.getString("description"))
                .duration(rowSet.getInt("duration"))
                .mpa(Rating.builder().
                        id(rowSet.getInt("rating_id"))
                        .name(rowSet.getString(7)).build())
                .genres(getGenreFilm((long) rowSet.getInt("id")))
                .likes(getLikes((long) rowSet.getInt("id")))
                .build();
    }


}


