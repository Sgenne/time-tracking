package com.sgenne.timetracking.user;

import com.sgenne.timetracking.error.exception.ResourceNotFoundException;
import com.sgenne.timetracking.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No user with the id " + id));
    }
}
