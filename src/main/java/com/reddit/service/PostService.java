package com.reddit.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.entity.User;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;

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

    @Transactional
    public void addPost(String title,String content,String subredditName,String username,MultipartFile file) throws IOException{
      Post post=new Post();
      post.setTitle(title);
      post.setContent(content);
      if(file.isEmpty()){
        System.out.println("File is empty");
      }
      else{
        post.setImage(file.getOriginalFilename());

        System.out.println(new ClassPathResource("").getFile().getAbsolutePath());
        System.out.println("///////////////////////");
       File saveFile= new ClassPathResource("static/img").getFile();
      Path path=  Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
       Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
      }

      post.setUser(userService.getUserByUsername(username));
      Subreddit subreddit=this.subredditRepository.findById((long)Integer.parseInt(subredditName)).get();
      post.setSubreddit(subreddit);
      this.postRepository.save(post);
      
    }
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
  
  public Page<Post> getAllPost(Integer pageNumber,Integer pageSize){
    Pageable pageable= PageRequest.of(pageNumber-1,pageSize);
    Page<Post> posts=this.postRepository.findAll(pageable);
    return posts;
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

  public List<Post> sortPostByDate(String postId) {
    String id[]=postId.split(",");
    List<Long> ids=new ArrayList<>();
    for(String single:id){
    ids.add(Long.parseLong(single));
    }
    List<Post> posts =new ArrayList<>();
  List<Post> list=  this.postRepository.sortPostByCreatedDate();
  for(Post post:list){
    if(ids.contains(post.getId())){
      posts.add(post);
    }
  }
    return posts;
  }

  public List<Post> sortPostByVoteCount() {
    List<Post> posts = this.postRepository.sortPostByVoteCount();
    return posts;
  }
  public List<Post> sortPostByVoteCountLoggedIn(String username) {
    List<Post> posts = this.postRepository.sortPostByVoteCount(userRepository.findByUsername(username).get().getId());
    return posts;
  }
  public List<Post> sortPostByDateLoggedIn(String username) {
    List<Post> posts = this.postRepository.sortPostByCreatedDate(userRepository.findByUsername(username).get().getId());
    return posts;
  }
  public List<Post> sortPostByVoteCount(String postId) {
    String id[]=postId.split(",");
    List<Long> ids=new ArrayList<>();
    for(String single:id){
    ids.add(Long.parseLong(single));
    }
    List<Post> posts =new ArrayList<>();
  List<Post> list=  this.postRepository.sortPostByVoteCount();
  for(Post post:list){
    if(ids.contains(post.getId())){
      posts.add(post);
    }
  }
    return posts;
  }
  

  @Transactional
  public void deletePost(Long postId) {
    Post post = this.postRepository.findPostById(postId);
    post.setDownvotedUsers(null);
    post.setUpvotedUsers(null);
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
    Optional<User> result = userRepository.findByUsername(username);
    User user = null;
    if (result != null) {
      user = result.get();
      System.out.println(">> username : " + user.getUsername());
      if (!post.getUpvotedUsers().contains(user)) {
        post.getUpvotedUsers().add(user);
        if (post.getDownvotedUsers().contains(user)) {
          post.getDownvotedUsers().remove(user);
        }
      } else {
        post.getUpvotedUsers().remove(user);
      }
      post.setVoteCount(post.getUpvotedUsers().size()-post.getDownvotedUsers().size());
      this.postRepository.save(post);
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
      if (!post.getDownvotedUsers().contains(user)) {
        post.getDownvotedUsers().add(user);
        if (post.getUpvotedUsers().contains(user)) {
          post.getUpvotedUsers().remove(user);
        }
      } else {
        post.getDownvotedUsers().remove(user);
      }
      post.setVoteCount(post.getUpvotedUsers().size()-post.getDownvotedUsers().size());
      this.postRepository.save(post);
    }
  }
  public List<Post> searchPost(String searchBy){
    List<User> users=this.userService.findAllUser();
    List<Post> posts=new ArrayList<>();
    for(User user:users){
      if(user.getUsername().equalsIgnoreCase(searchBy)){
       posts=this.userService.getPosts(searchBy);
       return posts;
      }
    }
   posts =this.postRepository.findPostBySubredditNameOrPostTitle(searchBy);
    return posts;
  }


  public List<Post> sortPost(String searchBy){
   List<Post> posts =this.postRepository.findSortBySubredditNameOrPostTitle(searchBy);
    return posts;
  }

  public List<Post> topPost(String searchBy){
    List<Post> posts =this.postRepository.findTopBySubredditNameOrPostTitle(searchBy);
     return posts;
   }


  public List<Post> getPostsFromSubredditsFollowedByUser(String name) {
    List<Post> posts =this.postRepository.getPostsFromSubredditsFollowedByUser(userRepository.findByUsername(name).get().getId());
    return posts;
  }
}
