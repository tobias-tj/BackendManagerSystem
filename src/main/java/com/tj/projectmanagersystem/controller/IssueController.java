package com.tj.projectmanagersystem.controller;


import com.tj.projectmanagersystem.dto.IssueDto;
import com.tj.projectmanagersystem.modal.Issue;
import com.tj.projectmanagersystem.modal.User;
import com.tj.projectmanagersystem.request.IssueRequest;
import com.tj.projectmanagersystem.response.AuthResponse;
import com.tj.projectmanagersystem.response.MessageResponse;
import com.tj.projectmanagersystem.service.IssueService;
import com.tj.projectmanagersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;


    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDto> createIssue(@RequestBody IssueRequest issue, @RequestHeader("Authorization") String token) throws Exception {
        User userToken = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(userToken.getId());

        Issue createIssue = issueService.createIssue(issue, user);
        IssueDto issueDto = new IssueDto();
        issueDto.setDescription(createIssue.getDescription());
        issueDto.setDueDate(createIssue.getDueDate());
        issueDto.setId(createIssue.getId());
        issueDto.setPriority(createIssue.getPriority());
        issueDto.setProject(createIssue.getProject());
        issueDto.setProjectID(createIssue.getProject().getId());
        issueDto.setStatus(createIssue.getStatus());
        issueDto.setTitle(createIssue.getTitle());
        issueDto.setTags(createIssue.getTags());
        issueDto.setAssignee(createIssue.getAssignee());

        return ResponseEntity.ok(issueDto);

    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
                                                       @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Issue Deleted");

        return ResponseEntity.ok(res);

    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
                                                @PathVariable Long userId) throws Exception{
        Issue issue = issueService.addUserToIssue(issueId, userId);

        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable String status,
                                                   @PathVariable Long issueId) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);

        return ResponseEntity.ok(issue);
    }


}
