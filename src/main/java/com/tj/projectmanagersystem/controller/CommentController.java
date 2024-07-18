package com.tj.projectmanagersystem.controller;

import com.tj.projectmanagersystem.modal.Comment;
import com.tj.projectmanagersystem.modal.User;
import com.tj.projectmanagersystem.request.CreateCommentReq;
import com.tj.projectmanagersystem.response.MessageResponse;
import com.tj.projectmanagersystem.service.CommentService;
import com.tj.projectmanagersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentReq req,
                                                 @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Comment comment = commentService.createComment(req.getIssueId(), user.getId(), req.getContent());
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId,
                                                         @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Comment Deleted Successfully!");
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(@PathVariable Long issueId){
        List<Comment> commentList = commentService.findCommentByIssueId(issueId);
        return ResponseEntity.ok(commentList);
    }
}
