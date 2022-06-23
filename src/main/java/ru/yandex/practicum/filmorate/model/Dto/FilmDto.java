package ru.yandex.practicum.filmorate.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.utils.annotation.Duration;
import ru.yandex.practicum.filmorate.utils.annotation.Length;
import ru.yandex.practicum.filmorate.utils.annotation.NotEmpty;
import ru.yandex.practicum.filmorate.utils.annotation.ReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmDto {

    private Long id;
    @NotEmpty
    private String name;
    @Length
    private String description;
    @ReleaseDate
    private LocalDate releaseDate;
    @Duration
    private Integer duration;

    private Rating mpa;

    Set<Genre> genres = new HashSet<>();

    private final Set<Long> likes = new HashSet<>();
}
