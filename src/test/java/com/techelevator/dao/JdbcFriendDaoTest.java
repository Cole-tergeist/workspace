package com.techelevator.dao;

import com.techelevator.model.Friend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcFriendDaoTest extends BaseDaoTest {
    protected static final Friend FRIEND_REQUEST_1 = new Friend(5, 1, 2, "PENDING");
    protected static final Friend FRIEND_REQUEST_2 = new Friend(2, 2, 1, "ACCEPTED");
    private JdbcFriendDao dao;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcFriendDao(jdbcTemplate);

    }

    @Test
    public void getFriendsForUser_invalidId_returnsEmptyList() {
        List<Friend> friends = dao.getFriendsForUser(-1);

        assertNotNull(friends, "Expected non-null list");
        assertTrue(friends.isEmpty(), "Expected empty list for invalid user ID");
    }

    @Test
    public void sendFriendRequest_validData_createsFriendRequest() {
        Friend friendRequest = new Friend(3, 3, 2, "PENDING");
        Friend createdRequest = dao.sendFriendRequest(friendRequest);


        assertNotNull(createdRequest, "Expected non-null friend request after creation");
        assertEquals("PENDING", createdRequest.getStatus(), "Expected status to be PENDING");
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void deleteFriend_validId_deletesSuccessfully() {
        Friend friendRequest = dao.sendFriendRequest(new Friend(0, 3, 4, "PENDING"));
        int friendId = friendRequest.getFriendId();
        assertTrue(dao.deleteFriend(friendId), "Expected delete to return true");
        assertNull(dao.getFriendById(friendId), "Deleted friend request should not exist");
    }
}
