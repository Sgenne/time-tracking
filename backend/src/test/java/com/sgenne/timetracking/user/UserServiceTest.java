package com.sgenne.timetracking.user;

import com.sgenne.timetracking.error.exception.ResourceNotFoundException;
import com.sgenne.timetracking.user.model.Role;
import com.sgenne.timetracking.user.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Test
    void saveUser() {
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.save(any()))
                .thenAnswer(i -> i.getArguments()[0]);

        UserService userService = new UserService(userRepository);

        String username = "username";
        String password = "password";
        List<Role> roles = List.of(Role.USER);

        User inputUser = new User(username, password, roles);

        User resultUser = userService.saveUser(inputUser);

        assertEquals(inputUser, resultUser);
    }

    @Test
    void getUserById_userFound() {
        String username = "username";
        String password = "password";
        List<Role> roles = List.of(Role.USER);
        Long userId = 1L;

        User user = new User(username, password, roles);

        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        UserService userService = new UserService(userRepository);

        User resultUser = userService.getUserById(userId);

        assertEquals(resultUser, user);
    }

    @Test
    void getUserById_userNotFound() {
        Long userId = 1L;

        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.ofNullable(null));

        UserService userService = new UserService(userRepository);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
    }


}