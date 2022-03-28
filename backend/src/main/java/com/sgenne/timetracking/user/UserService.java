package com.sgenne.timetracking.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    /**
     * Adds a user to persistent storage.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Returns the user with the given id.
     * @param userId
     */
    public User getUserById(Integer userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("No user with id %s was found.", userId)));
    }

    /**
     * Updates the role of a user.
     * @param userId The id of the user to be updated.
     * @param role The new role of the specified user.
     */
    public void updateUserRole(Integer userId, Role role) {
        User user = getUserById(userId);
        user.setRole(role);
    }

    public Collection<User> getAllUsers() {
        return userRepository.findAll();

    }
}
