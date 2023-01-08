package com.reddit.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.entity.User;
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
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  public void addPost(Post post) {
    this.postRepository.save(post);
  }

  @Transactional
  public void addPost(String title, String content, String subredditName, String username) {
    Post post = new Post();
    post.setTitle(title);
    post.setContent(content);
    post.setUser(userService.getUserByUsername(username));
    Subreddit subreddit = this.subredditRepository.findById((long) Integer.parseInt(subredditName)).get();
    post.setSubreddit(subreddit);
    this.postRepository.save(post);

  }

  public List<Post> getAllPost() {
    List<Post> posts = this.postRepository.findAll();
    return posts;
  }

  public Post getPostById(Long id) {
    Post post = this.postRepository.findPostById(id);
    return post;
  }

  public List<Post> getAllPosts() {
    List<Post> posts = this.postRepository.findAll();
    return posts;
  }

  public List<Post> sortPostByDate() {
    List<Post> posts = this.postRepository.sortPostByCreatedDate();
    return posts;
  }

  public List<Post> sortPostByVoteCount() {
    List<Post> posts = this.postRepository.sortPostByVoteCount();
    return posts;
  }

  @Transactional
  public void deletePost(Long postId) {
    Post post = this.postRepository.findPostById(postId);
    this.postRepository.delete(post);
  }

  @Transactional
  public void updatePost(Long postId, String title, String content, String subredditName, String username) {
    Post post = this.postRepository.findPostById(postId);
    post.setTitle(title);
    post.setContent(content);

    Subreddit subreddit = this.subredditRepository.findById((long) Integer.parseInt(subredditName)).get();
    post.setSubreddit(subreddit);

    post.setUser(this.userService.getUserByUsername(username));

    this.postRepository.save(post);

  }

  @Transactional
  public void upvotePost(Long postId, String username) {
    Post post = postRepository.findPostById(postId);
    System.out.println(">> post title : " + post.getTitle());
    Optional<User> result = userRepository.findByUsername(username);
    User user = null;
    if (result != null) {
      user = result.get();
      System.out.println(">> username : " + user.getUsername());
      if (!post.getUpvotedUser().contains(user)) {
        post.getUpvotedUser().add(user);
        if (post.getDownvotedUser().contains(user)) {
          post.getDownvotedUser().remove(user);
        }
      } else {
        post.getUpvotedUser().remove(user);
      }
    }
  }

  @Transactional
  public void downvotePost(Long postId, String username) {
    Post post = postRepository.findPostById(postId);
    System.out.println(">> post title : " + post.getTitle());
    Optional<User> result = userRepository.findByUsername(username);
    User user = null;
    if (result != null) {
      user = result.get();
      System.out.println(">> username : " + user.getUsername());
      if (!post.getDownvotedUser().contains(user)) {
        post.getDownvotedUser().add(user);
        if (post.getUpvotedUser().contains(user)) {
          post.getUpvotedUser().remove(user);
        }
      } else {
        post.getDownvotedUser().remove(user);
      }
    }
  }
}
