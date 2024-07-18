package com.tj.projectmanagersystem.service;

import com.tj.projectmanagersystem.modal.Chat;
import com.tj.projectmanagersystem.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    private ChatRepository chatRepository;
    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
