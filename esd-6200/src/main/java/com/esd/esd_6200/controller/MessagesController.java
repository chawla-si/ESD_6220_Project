package com.esd.esd_6200.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import com.esd.esd_6200.dao.MessageRepository;
import com.esd.esd_6200.pojo.Message;
import com.esd.esd_6200.requestModels.AdminQuestionRequest;
import com.esd.esd_6200.service.MessagesService;
import com.esd.esd_6200.utils.ExtractJWT;


@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {
    private MessagesService messagesService;
    private MessageRepository messageRepository;
    
    @Autowired
    public MessagesController(MessagesService messagesService, MessageRepository messageRepository) {
        this.messagesService = messagesService;
        this.messageRepository = messageRepository;
    }
    
    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value="Authorization") String token,
                            @RequestBody Message messageRequest) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messagesService.postMessage(messageRequest, userEmail);
    }
    
    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value="Authorization") String token,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }
        messagesService.putMessage(adminQuestionRequest, userEmail);
    }
    
    // Add this method to handle the findByClosed endpoint
    @GetMapping("/search/findByClosed")
    public Page<Message> findByClosed(@RequestParam("closed") boolean closed,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "5") int size) {
        return messageRepository.findByClosed(closed, PageRequest.of(page, size));
    }
    
    // Also add this method for findByUserEmail endpoint
    @GetMapping("/search/findByUserEmail")
    public Page<Message> findByUserEmail(@RequestParam("userEmail") String userEmail,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        return messageRepository.findByUserEmail(userEmail, PageRequest.of(page, size));
    }
}