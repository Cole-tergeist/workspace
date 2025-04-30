package com.techelevator.dao;

import com.techelevator.model.Message;
import java.util.List;

public interface MessageDao {
    Message createMessage(Message message);
    List<Message> getMessagesBetweenUsers(int senderId, int receiverId);
    List<Message> getMessagesForUser(int userId);

    boolean deleteMessage(int messageId);
}
