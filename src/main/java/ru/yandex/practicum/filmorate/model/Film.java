package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.utils.annotation.Duration;
import ru.yandex.practicum.filmorate.utils.annotation.Length;
import ru.yandex.practicum.filmorate.utils.annotation.NotEmpty;
import ru.yandex.practicum.filmorate.utils.annotation.ReleaseDate;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    private Long id;

    @NotEmpty
    private String name;
    @Length
    private String description;
    @Duration
    private Integer duration;
    @ReleaseDate
    private LocalDate releaseDate;


}
