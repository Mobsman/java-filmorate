package ru.yandex.practicum.filmorate;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.inDbStorage.FilmDbStorage;
import ru.yandex.practicum.filmorate.inDbStorage.UserDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;


	@Test
	public void testFindUserById() throws UserNotFoundException {
		User user = userStorage.getById(1l);
		Assertions.assertEquals(1, user.getId());
	}

	@Test
	public void updateTestUser() throws ValidationException, UserNotFoundException {
		User newUserForUpdate = userStorage.getById(1l);
		newUserForUpdate.setLogin("test");
		newUserForUpdate.setBirthday(LocalDate.of(1950, 10, 30));
		newUserForUpdate.setEmail("ttt@ttt.ru");
		userStorage.update(newUserForUpdate);

		User updatedUser = userStorage.getById(1l);
		Assertions.assertEquals(updatedUser.getId(), newUserForUpdate.getId());
		Assertions.assertEquals(newUserForUpdate.getLogin(), updatedUser.getLogin());
		Assertions.assertEquals(newUserForUpdate.getName(), updatedUser.getName());
		Assertions.assertEquals(newUserForUpdate.getBirthday(), updatedUser.getBirthday());
		Assertions.assertEquals(newUserForUpdate.getEmail(), updatedUser.getEmail());
	}

	@Test
	public void testAddToFriends() throws UserNotFoundException {
		User user = userStorage.getById(1l);
		User friend = userStorage.getById(2l);
		userStorage.addFriend(user.getId(), friend.getId());
		user = userStorage.getById(user.getId());
		Assertions.assertTrue(user.getFriends().contains(2l));
	}

	@Test
	public void testDeleteFromFriends() throws UserNotFoundException {
		User user = userStorage.getById(1l);
		User friend = userStorage.getById(2l);
		userStorage.removeFriend(user.getId(), friend.getId());
		user = userStorage.getById(user.getId());
		Assertions.assertFalse(user.getFriends().contains(2));
	}

	@Test
	public void testGetAllUsers() {
		Assertions.assertNotNull(userStorage.getAll());
	}

	@Test
	public void testFindFilmById() throws FilmNotFoundException {
		Film film = filmStorage.getById(1l);
		Assertions.assertEquals(film, film);
	}


	@Test
	public void testAddLike() throws FilmNotFoundException {
		Film film = filmStorage.getById(1l);
		filmStorage.addLike(film.getId(), 2l);
		film = filmStorage.getById(1l);
		Assertions.assertTrue(film.getLikes().contains(2));
	}

	@Test
	public void testDeleteLike() throws FilmNotFoundException {
		Film film = filmStorage.getById(1l);
		filmStorage.deleteLike(film.getId(), 2l);
		film = filmStorage.getById(1l);
		Assertions.assertFalse(film.getLikes().contains(2));
	}

	@Test
	public void testGetAllFilms() {
		Assertions.assertNotNull(filmStorage.getAll());
	}

}