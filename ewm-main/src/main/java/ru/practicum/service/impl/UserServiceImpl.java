package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        return null;
    }

    @Override
    public UserDto addNewUser(NewUserRequest newUserRequest) {
        return null;
    }

    @Override
    public void deleteUserById(Integer userId) {

    }
}
