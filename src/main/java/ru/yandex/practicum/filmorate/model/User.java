package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.utils.annotation.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    @Duration
    private Long id;

    @Email
    private String email;
    @NotEmpty
    private String login;
    @CheckName
    private String name;
    @DateOfBirth
    private LocalDate birthday;

}
