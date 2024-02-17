package com.comment1.controller;

import com.comment1.entity.Comment;
import com.comment1.service.CommentService;
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

    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment){
        Comment savedComment = commentService.saveComment(comment);
        return new ResponseEntity<>(savedComment,HttpStatus.OK);
    }
    //http://localhost:8082/api/comments/{postId}
    @GetMapping("/{postId}")
    public List<Comment> getAllCommentByPostId(@PathVariable String postId){
       List<Comment> comments =  commentService.getAllCommentByPostId(postId);
       return comments;
    }

}
