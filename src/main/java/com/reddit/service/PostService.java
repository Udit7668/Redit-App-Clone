package com.reddit.service;

import com.reddit.entity.Post;
import com.reddit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void addPost(Post post){
        this.postRepository.save(post);
    }

    public Post getPostById(Long id){
      Post post= this.postRepository.findPostById(id);
      return post;
    }

    public List<Post> getAllPosts(){
        List<Post> posts=this.postRepository.findAll();
        return posts;
    }

}
