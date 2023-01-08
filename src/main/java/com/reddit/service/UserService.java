package com.reddit.service;

import com.reddit.entity.Comment;
import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.entity.User;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    public User getUserByUsername(String username){
     Optional<User> userOptional=  this.userRepository.findByUsername(username);
     User user=userOptional.get();
     return user;
    }
    public List<Post> getPosts(String username){
        User user = userRepository.findByUsername(username).get();
        return user.getPosts();
    }
    public List<Comment> getComments(String username){
        User user = userRepository.findByUsername(username).get();
        return user.getComments();
    }
    public List<Subreddit> getSubreddits(String username){
        User user = userRepository.findByUsername(username).get();
        return user.getSubreddits();
    }
  public List<User> findAllUser(){
   List<User> users= this.userRepository.findAll();
    return users;
  }
}
