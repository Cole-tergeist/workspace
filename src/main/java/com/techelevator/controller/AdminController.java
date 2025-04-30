package com.techelevator.controller;

import com.techelevator.dao.AppointmentDao;
import com.techelevator.dao.FriendDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Appointment;
import com.techelevator.model.Friend;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin
public class AdminController {

    private final UserDao userDao;
    private final AppointmentDao appointmentDao;
    private final FriendDao friendDao;

    public AdminController(UserDao userDao, AppointmentDao appointmentDao, FriendDao friendDao) {
        this.userDao = userDao;
        this.appointmentDao = appointmentDao;
        this.friendDao = friendDao;
    }



    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userDao.getUsers();
        if (users == null || users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }
        return users;
    }

    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = appointmentDao.getAllAppointments();
        if (appointments == null || appointments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointments found");
        }
        return appointments;
    }

    @GetMapping("/friends")
    public List<Friend> getAllFriends() {
        List<Friend> friends = friendDao.getAllFriends();
        if (friends == null || friends.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No friends found");
        }
        return friends;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User newUser) {
        if (userDao.getUserByUsername(newUser.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has an account");
        }
        return userDao.createUser(newUser);
    }
}
