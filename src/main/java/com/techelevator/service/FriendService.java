package com.techelevator.service;

import com.techelevator.dao.FriendDao;
import com.techelevator.model.Friend;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    private final FriendDao friendDao;

    public FriendService(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    public Friend sendFriendRequest(Friend friend) {
        return friendDao.sendFriendRequest(friend);
    }

    public List<Friend> getFriendsForUser(int userId) {
        return friendDao.getFriendsForUser(userId);
    }

    public List<Friend> getPendingRequests(int userId) {
        return friendDao.getPendingRequests(userId);
    }

    public Friend updateFriendStatus(Friend friend) {
        return friendDao.updateFriendStatus(friend);
    }

    public boolean removeFriend(int friendId) {
        return friendDao.removeFriend(friendId);
    }

    public List<Friend> getAllFriends() {
        return friendDao.getAllFriends();
    }
}
