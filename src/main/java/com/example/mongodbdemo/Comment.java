package com.example.mongodbdemo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author 王鹏
 */
@Data
@Document
public class Comment {
    @Id
    private Integer id;

    private String user;

    private String comment;

    private Date createTime;

    private Integer level = 0;

    private List<String> likeUserList;

    private List<Comment> subComment;

    private Integer likeCount;
}
