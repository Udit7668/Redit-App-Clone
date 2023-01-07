package com.reddit.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

@Autowired
     private UserService userService;

    public void addPost(Post post){
      this.postRepository.save(post);
    }

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
      this.postRepository.save(post);
    }

    @Transactional
    public void downVote(Long id){
      Post post=this.postRepository.findPostById(id);
       Integer oldCount=  post.getVoteCount();
       post.setVoteCount(oldCount-1);
      this.postRepository.save(post);
    }


    public List<Post> sortPostByVoteCount(){
      List<Post> posts=this.postRepository.sortPostByVoteCount();
      return posts;
    }


    @Transactional
    public void deletePost(Long postId){
    Post post=this.postRepository.findPostById(postId);
    this.postRepository.delete(post);
    }


    @Transactional
    public void updatePost(Long postId,String title,String content,String subredditName,String username){
     Post post=this.postRepository.findPostById(postId);
     post.setTitle(title);
     post.setContent(content);

     Subreddit subreddit=this.subredditRepository.findById((long)Integer.parseInt(subredditName)).get();
     post.setSubreddit(subreddit);

     post.setUser(this.userService.getUserByUsername(username));

     this.postRepository.save(post);

    }
}
