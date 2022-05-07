package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.utils.annotation.Duration;
import ru.yandex.practicum.filmorate.utils.annotation.Length;
import ru.yandex.practicum.filmorate.utils.annotation.NotEmpty;
import ru.yandex.practicum.filmorate.utils.annotation.ReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film implements Comparable<Film> {

    private Long id;

    @NotEmpty
    private String name;
    @Length
    private String description;
    @Duration
    private Integer duration;
    @ReleaseDate
    private LocalDate releaseDate;

    private final Set<Long> likes = new HashSet<>();


    @Override
    public int compareTo(Film o) {
        int a = this.getLikes().size();
        int b = o.getLikes().size();
        return b - a ;
    }
}
