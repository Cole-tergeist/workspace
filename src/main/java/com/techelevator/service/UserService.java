package com.techelevator.service;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Create a new user
    public User createUser(User newUser) {
        if (newUser.getHashedPassword() == null || newUser.getUsername() == null) {
            throw new IllegalArgumentException("Username and password cannot be null.");
        }

        // Hash password before saving
        newUser.setHashedPassword(passwordEncoder.encode(newUser.getHashedPassword()));

        return userDao.createUser(newUser);
    }

    // Get a user by ID
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    // Admin Only
    public List<User> getUsers() {
        return userDao.getUsers();
    }



    // Admin Only
    public boolean deleteUser(int userId) {
        return userDao.deleteUser(userId);
    }
}