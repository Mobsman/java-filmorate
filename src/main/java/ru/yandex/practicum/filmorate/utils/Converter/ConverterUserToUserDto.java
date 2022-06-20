package ru.yandex.practicum.filmorate.utils.Converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

@Service
public class ConverterUserToUserDto implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {
       return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .login(user.getLogin())
                .birthday(user.getBirthday())
                .build();
    }

}
