package com.mybooks.backend.service;

import com.mybooks.backend.exception.UserNotFoundException;
import com.mybooks.backend.model.User;
import com.mybooks.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        Optional<User> dbUser =  userRepository.findUserByUserId(user.getUserId());
        if (Boolean.FALSE.equals(dbUser.isPresent())) {
            Integer userType = user.getUserId().toUpperCase().startsWith("CUSTOMER") ? 0 :
                    user.getUserId().toUpperCase().startsWith("EXPERT") ? 1 : -1;
            user.setUserType(userType);
            return userRepository.save(user);
        }
        return userRepository.save(dbUser.get());
    }

    public User findUserByUserId(String userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(() -> new UserNotFoundException("User by id " +
                userId + " was not found"));
    }

}
