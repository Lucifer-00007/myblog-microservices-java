package com.post1.service;

import com.post1.config.RestTemplateConfig;
import com.post1.entity.Post;
import com.post1.payload.PostDto;
import com.post1.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplateConfig restTemplate;

    public Post savePost(Post post){
        String postId = UUID.randomUUID().toString();
        post.setId(postId);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    public Post getPostByPostId(String postId) {
        Post post = postRepository.findById(postId).get();
        return post;
    }

    public PostDto getCommenWithPost(String postId) {
       Post post = postRepository.findById(postId).get();
        ArrayList comments = restTemplate.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/comments/" + postId, ArrayList.class);
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getId());
        postDto.setTitle(post.getTitle());
         postDto.setDescription(post.getDescription());
         postDto.setContent(post.getContent());
         postDto.setComments(comments);
         return postDto;
    }
}
