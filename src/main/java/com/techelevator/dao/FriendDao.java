package com.techelevator.dao;

import com.techelevator.model.Friend;
import java.util.List;

public interface FriendDao {
    Friend sendFriendRequest(Friend friend);
    List<Friend> getFriendsForUser(int userId);
    List<Friend> getPendingRequests(int userId);
    Friend updateFriendStatus(Friend friend);
    boolean removeFriend(int friendId);
    boolean deleteFriend(int friendId);
    List<Friend> getAllFriends();
    // Friend createFriendRequest(Friend friend);
}