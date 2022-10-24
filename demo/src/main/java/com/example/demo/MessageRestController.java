package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageRestController {
    @Autowired
    private MessageRepo messageRepo;
    private SimpMessagingTemplate template;

    @Autowired
    public MessageRestController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @GetMapping()
    public ResponseEntity<List<MessageEntity>> getMessages() {
        try {
            List<MessageEntity> messages = messageRepo.findAll();
            return ResponseEntity.ok().body(messages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ArrayList<>());
        }
    }
    @DeleteMapping()
    public <T> ResponseEntity<T> deleteAll() {
        try {
            messageRepo.deleteAll();
            this.template.convertAndSend("/webs/delete", true);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
