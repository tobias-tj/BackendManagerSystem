package com.tj.projectmanagersystem.service;

import com.tj.projectmanagersystem.modal.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, Long projectId, String content) throws Exception;

    List<Message> getMessageByProjectId(Long projectId) throws Exception;
}
