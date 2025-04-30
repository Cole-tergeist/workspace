package com.techelevator.controller;

import com.techelevator.dao.FriendDao;
import com.techelevator.model.Friend;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/friends")
@CrossOrigin
public class FriendController {

    private final FriendDao friendDao;

    public FriendController(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Friend sendFriendRequest(@RequestBody Friend friend) {
        return friendDao.sendFriendRequest(friend);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("permitAll")
    public List<Friend> getFriendList(@PathVariable int userId) {
        List<Friend> friends = friendDao.getFriendsForUser(userId);
        if (friends == null || friends.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No friends found");
        }
        return friends;
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFriend(@RequestParam int friendId) {
        boolean deleted = friendDao.deleteFriend(friendId);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend not found");
        }
    }
}
