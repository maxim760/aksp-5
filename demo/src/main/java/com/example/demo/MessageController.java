package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class MessageController {
    @Autowired
    private MessageRepo messageRepo;

    @MessageMapping("/chat")
    @SendTo("/webs/messages")
    public MessageEntity send(Message message) {
        MessageEntity msg = new MessageEntity();
        String date = new SimpleDateFormat("d MMMM HH:mm", new Locale("ru")).format(new Date());
        msg.setDate(date);
        msg.setText(message.getText());
        msg.setUsername(message.getFrom());
        messageRepo.save(msg);
        System.out.println(msg.getText() + " " + msg.getId());
        return msg;
    }
}
