package ru.yandex.practicum.filmorate.exception;

public class RatingNotFoundException extends Exception {
    public RatingNotFoundException (String message) {
        super(message);
    }
}