package com.comment1.service;

import com.comment1.config.RestTemplateConfig;
import com.comment1.entity.Comment;
import com.comment1.payload.Post;
import com.comment1.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestTemplateConfig restTemplate;

    public Comment saveComment(Comment comment){
        Post post = restTemplate.getRestTemplate().getForObject("http://localhost:8081/api/posts/" + comment.getPostId(), Post.class);
      if(post!=null){
          String commentId = UUID.randomUUID().toString();
          comment.setCommentId(commentId);
          Comment savedComment = commentRepository.save(comment);
          return savedComment;
      }else{
          return null;
      }
    }


    public List<Comment> getAllCommentByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments;
    }
}
