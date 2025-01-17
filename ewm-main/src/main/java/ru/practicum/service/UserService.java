package ru.practicum.service;

import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto addNewUser(NewUserRequest newUserRequest);

    void deleteUserById(Long userId);
}
