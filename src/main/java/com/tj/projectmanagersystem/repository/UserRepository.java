package com.tj.projectmanagersystem.repository;

import com.tj.projectmanagersystem.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
