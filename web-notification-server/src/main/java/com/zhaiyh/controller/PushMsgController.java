package com.zhaiyh.controller;

import com.zhaiyh.entity.PushMessage;
import com.zhaiyh.socket.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("push")
public class PushMsgController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public String push(@RequestBody PushMessage message) {
        messageService.sendToTag(message.getTags(), message.getMessage());
        messageService.sendToUser(message.getUsers(), message.getMessage());
        return "{\"status\": true}";
    }
}
