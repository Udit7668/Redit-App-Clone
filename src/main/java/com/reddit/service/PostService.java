package com.reddit.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import com.reddit.repository.UserRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubredditRepository subredditRepository;

@Autowired
     private UserService userService;

    public void addPost(Post post){
      this.postRepository.save(post);
    }

    @Transactional
    public void addPost(String title,String content,String subredditName,String username){
      Post post=new Post();
      post.setTitle(title);
      post.setContent(content);
      post.setUser(userService.getUserByUsername(username));
      Subreddit subreddit=this.subredditRepository.findSubredditByName(subredditName);
      post.setSubreddit(subreddit);
      this.postRepository.save(post);
      
    }


    public List<Post> getAllPost(){
      List<Post> posts=this.postRepository.findAll();
      return posts;
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


    @Transactional
    public void upVote(Long id){
      Post post=this.postRepository.findPostById(id);
       Integer oldCount=  post.getVoteCount();
      post.setVoteCount(oldCount+1);
      System.out.println("**************" +(oldCount+1));
      this.postRepository.save(post);
    }

}
