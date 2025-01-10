package ru.practicum.controller.adminApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.user.NewUserRequest;
import ru.practicum.model.dto.user.UserDto;
import ru.practicum.service.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Integer> ids,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /admin/users is accessed");
        return userService.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addNewUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("POST /admin/users is accessed: {}", newUserRequest);
        return userService.addNewUser(newUserRequest);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Integer userId) {
        log.info("DELETE /admin/users/{} is accessed", userId);
        userService.deleteUserById(userId);
    }
}
