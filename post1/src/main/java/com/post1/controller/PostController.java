package com.post1.controller;

import com.post1.entity.Post;
import com.post1.payload.PostDto;
import com.post1.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    public PostService postService;

    //http://localhost:8081/api/posts
    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody Post post){
        Post savedpost = postService.savePost(post);
        return new ResponseEntity<>(savedpost, HttpStatus.OK);
    }
    //http://localhost:8081/api/posts/{postId}
    @GetMapping("/{postId}")
    public Post getCommentByPostId(@PathVariable String postId){
        Post post = postService.getPostByPostId(postId);
        return post;
    }
    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name = "commentBreaker" , fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getCommenWithPost(@PathVariable String postId){
        PostDto postDto = postService.getCommenWithPost(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    public ResponseEntity<PostDto> commentFallback(String postId, Exception ex){
        System.out.println("fallback is executed because server is down"+ex.getMessage());
        ex.printStackTrace();


        PostDto dto = new PostDto();
        dto.setPostId("1234");
        dto.setTitle("Service Down");
        dto.setDescription("Service Down");
        dto.setContent("Service Down");
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }

}
