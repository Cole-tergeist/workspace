package com.techelevator.controller;

import com.techelevator.dao.MessageDao;
import com.techelevator.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin
public class MessageController {

    private final MessageDao messageDao;

    public MessageController(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Message sendMessage(@RequestBody Message message) {
        return messageDao.createMessage(message);
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@RequestParam int messageId) {
        boolean deleted = messageDao.deleteMessage(messageId);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
    }
}
