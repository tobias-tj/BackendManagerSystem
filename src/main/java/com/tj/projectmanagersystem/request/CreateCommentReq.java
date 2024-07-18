package com.tj.projectmanagersystem.request;

import lombok.Data;

@Data
public class CreateCommentReq {
    private Long issueId;
    private String content;
}
