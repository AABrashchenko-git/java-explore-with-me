package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;
import ru.practicum.model.entity.User;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable page = PageRequest.of(from / size, size);
        List<User> users;
        if (ids == null) {
            users = userRepository.findAll(page).getContent();
        } else {
            users = userRepository.findAllById(ids);
        }
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto addNewUser(NewUserRequest newUserRequest) {
        if(userRepository.existsByEmail(newUserRequest.getEmail()))
            throw new ConflictException("user already exists");
        User newUser = userMapper.newUserRequestToUser(newUserRequest);
        return userMapper.userToUserDto(userRepository.save(newUser));
    }

    @Override
    public void deleteUserById(Long userId) {
        if(!userRepository.existsById(userId))
            throw new NotFoundException("user is not found");
        userRepository.deleteById(userId);
    }
}
