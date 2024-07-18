package com.tj.projectmanagersystem.controller;

import com.tj.projectmanagersystem.modal.Chat;
import com.tj.projectmanagersystem.modal.Message;
import com.tj.projectmanagersystem.modal.User;
import com.tj.projectmanagersystem.request.CreateMessageRequest;
import com.tj.projectmanagersystem.service.MessageService;
import com.tj.projectmanagersystem.service.ProjectService;
import com.tj.projectmanagersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request
                                               ) throws Exception {
        User user = userService.findUserById(request.getSenderId());
        if(user==null) throw new Exception("User is Invalid with id:" + request.getSenderId());
        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if(chats==null) throw new Exception("Chats not found!");
        Message sendMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sendMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessageByChatId(@PathVariable Long projectId) throws Exception{
        List<Message> messageList = messageService.getMessageByProjectId(projectId);
        return ResponseEntity.ok(messageList);
    }


}
