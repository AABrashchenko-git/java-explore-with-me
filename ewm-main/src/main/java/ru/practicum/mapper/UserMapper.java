package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;
import ru.practicum.model.dto.user.UserShortDto;
import ru.practicum.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User newUserRequestToUser(NewUserRequest newUserDto);
    UserDto userToUserDto(User user);
    UserShortDto userToUserShortDto(User user); // для EventFullDto
}
