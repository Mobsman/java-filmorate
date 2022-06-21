package ru.yandex.practicum.filmorate.utils.Converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;

@Service
public class ConverterFilmToFilmDto implements Converter<Film, FilmDto> {

    @Override
    public FilmDto convert(Film film) {

        return FilmDto.builder()
                .id(film.getId())
                .name(film.getName())
                .description(film.getDescription())
                .duration(film.getDuration())
                .releaseDate(film.getReleaseDate())
                .mpa(film.getMpa())
                .genres(film.getGenres().isEmpty() ? null : film.getGenres())
                .build();

    }
}
