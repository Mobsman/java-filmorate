package ru.yandex.practicum.filmorate.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.annotation.CheckName;
import ru.yandex.practicum.filmorate.utils.annotation.DateOfBirth;
import ru.yandex.practicum.filmorate.utils.annotation.Email;
import ru.yandex.practicum.filmorate.utils.annotation.NotEmpty;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto  {

    private Long id;
    @NotEmpty
    private String login;
    @CheckName
    private String name;
    @Email
    private String email;
    @DateOfBirth
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();
}
