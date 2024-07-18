package com.tj.projectmanagersystem.repository;

import com.tj.projectmanagersystem.modal.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    public List<Issue> findByProject_Id(Long id);
}
