package com.reddit.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubredditRepository subredditRepository;

    public void addPost(Post post){
      this.postRepository.save(post);
    }

    @Transactional
    public void addPost(String title,String content,String subredditName){
      Post post=new Post();
      post.setTitle(title);
      post.setContent(content);

      Subreddit subreddit=this.subredditRepository.findSubredditByName(subredditName);
      post.setSubreddit(subreddit);
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
