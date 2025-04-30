package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Friend;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFriendDao implements FriendDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcFriendDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Friend sendFriendRequest(Friend friend) {
        String sql = "INSERT INTO friends (requester_id, addressee_id, status) VALUES (?, ?, 'PENDING') RETURNING friend_id";
        try {
            Integer friendId = jdbcTemplate.queryForObject(sql, Integer.class,
                    friend.getRequesterId(), friend.getAddresseeId());
            return getFriendById(friendId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Friend updateFriendStatus(Friend friend) {
        String sql = "UPDATE friends SET status = ? WHERE friend_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, friend.getStatus(), friend.getFriendId());
            if (rowsAffected == 0) {
                throw new DaoException("No friend request found to update with ID: " + friend.getFriendId());
            }
            return getFriendById(friend.getFriendId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public List<Friend> getFriendsForUser(int userId) {
        List<Friend> friends = new ArrayList<>();
        String sql = "SELECT * FROM friends WHERE (requester_id = ? OR addressee_id = ?) AND status = 'ACCEPTED'";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
            while (results.next()) {
                friends.add(mapRowToFriend(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return friends;
    }

    @Override
    public List<Friend> getPendingRequests(int userId) {
        List<Friend> requests = new ArrayList<>();
        String sql = "SELECT * FROM friends WHERE addressee_id = ? AND status = 'PENDING'";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                requests.add(mapRowToFriend(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return requests;
    }

    @Override
    public List<Friend> getAllFriends() {
        List<Friend> friends = new ArrayList<>();
        String sql = "SELECT * FROM friends";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                friends.add(mapRowToFriend(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return friends;
    }

    Friend getFriendById(int friendId) {
        String sql = "SELECT * FROM friends WHERE friend_id = ?";
        try {
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, friendId);
            if (rs.next()) {
                return mapRowToFriend(rs);
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
    }

    @Override
    public boolean removeFriend(int friendId) {
        String sql = "DELETE FROM friends WHERE friend_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, friendId);
            System.out.println("Deleted Friend ID: " + friendId + ", Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public boolean deleteFriend(int friendId) {
        return removeFriend(friendId);
    }

    private Friend mapRowToFriend(SqlRowSet rs) {
        Friend friend = new Friend();
        friend.setFriendId(rs.getInt("friend_id"));
        friend.setRequesterId(rs.getInt("requester_id"));
        friend.setAddresseeId(rs.getInt("addressee_id"));
        friend.setStatus(rs.getString("status"));
        return friend;
    }
}
