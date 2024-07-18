package com.tj.projectmanagersystem.repository;

import com.tj.projectmanagersystem.modal.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
