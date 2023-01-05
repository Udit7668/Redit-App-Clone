package com.reddit.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reddit.entity.Post;
import com.reddit.repository.PostRepository;

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


    public List<Post> sortPostByDate(){
      List<Post> posts=  this.postRepository.sortPostByCreatedDate();
    return posts;
    }

}
