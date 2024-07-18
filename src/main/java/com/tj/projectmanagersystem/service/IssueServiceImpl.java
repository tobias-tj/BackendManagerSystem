package com.tj.projectmanagersystem.service;

import com.tj.projectmanagersystem.modal.Issue;
import com.tj.projectmanagersystem.modal.Project;
import com.tj.projectmanagersystem.modal.User;
import com.tj.projectmanagersystem.repository.IssueRepository;
import com.tj.projectmanagersystem.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception {
         Optional<Issue> issue = issueRepository.findById(issueId);
         if(issue.isPresent()){
             return issue.get();
         }
         throw new Exception("NO issue found with issueId" + issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProject_Id(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issue, User user) throws Exception {
        Project project = projectService.getProjectById(issue.getProjectId());
        Issue issueData = new Issue();
        issueData.setTitle(issue.getTitle());
        issueData.setDescription(issue.getDescription());
        issueData.setStatus(issue.getStatus());
        issueData.setPriority(issue.getPriority());
        issueData.setDueDate(issue.getDueDate());

        issueData.setProject(project);

        return issueRepository.save(issueData);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);

        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
