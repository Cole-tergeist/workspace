package com.techelevator.dao;

import com.techelevator.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcMessageDaoTest extends BaseDaoTest {
    protected static final Message MESSAGE_1 = new Message(1, 1, 3, "Hi Dr. Emily!");
    protected static final Message MESSAGE_2 = new Message(2, 3, 1, "Hello Alice!");
    private JdbcMessageDao dao;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcMessageDao(jdbcTemplate);
    }

    @Test
    public void getMessageById_invalidId_returnsNull() {
        Message message = dao.getMessageById(-1);
        assertNull(message, "Expected null for invalid message ID");
    }

    @Test
    public void createMessage_validData_createsMessage() {
        Message newMessage = new Message(0, 1, 2, "Hello!");
        Message createdMessage = dao.createMessage(newMessage);

        assertNotNull(createdMessage, "Expected non-null message after creation");
        assertEquals("Hello!", createdMessage.getMessageText(), "Expected message text to be 'Hello!'");
    }

    @Test
    public void deleteMessage_validId_deletesSuccessfully() {
        int messageId = 1;
        assertTrue(dao.deleteMessage(messageId), "Expected delete to return true");
        assertNull(dao.getMessageById(messageId), "Deleted message should not exist");
    }
}
