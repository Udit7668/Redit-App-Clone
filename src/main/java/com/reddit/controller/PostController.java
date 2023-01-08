package com.reddit.controller;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.reddit.entity.Comment;
import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.service.PostService;
import com.reddit.service.SubredditService;


@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private SubredditService subredditService;

    @Autowired
    private PostService postService;

    @GetMapping("/newPost")
    public String createNewPost(Model model,Authentication authentication){
        Post post = new Post();
        String username=authentication.getName();
        model.addAttribute("username", username);
        List<Subreddit> subreddits = subredditService.findAll();
        model.addAttribute("post", post);
        model.addAttribute("subreddits", subreddits);
        return "new-post";
    }

    @PostMapping("/savepost")
    public String saveOrUpdatePost(@RequestParam("title") String title,
                                   @RequestParam("content") String content,
                                   @RequestParam("subreddit") String subreddit,
                                   @RequestParam("fileprofile") MultipartFile file,
                                   @RequestParam("username") String username) throws IOException{
       
        this.postService.addPost(title,content,subreddit,username,file);
        return "redirect:/posts/";
    }

    @GetMapping("/")
    public String showAllPost(Model model){
    List<Post> posts=this.postService.getAllPost();
    model.addAttribute("posts",posts);
    List<Subreddit> subreddits=this.subredditService.findAll();
    model.addAttribute("subreddits", subreddits);
        return "home";
    }

    @GetMapping("/view/{viewId}")
    @Transactional
    public String getPostById(@PathVariable("viewId") int postId,Model model){
        Post post=this.postService.getPostById((long)postId);
        Comment comment=new Comment();
        model.addAttribute("Comment", comment);
        model.addAttribute("post",post);
        return "viewPost";
    }

    @GetMapping("/upvote")
    public String upVote(@RequestParam("postId") Long postId, @RequestParam("username") String username ){
        System.out.println(">> upvoting : " + postId + " " + username);
        this.postService.upvotePost(postId, username); 
        return "redirect:/posts/";
    }

    @GetMapping("/downvote")
    public String downVote(@RequestParam("postId") Long postId, @RequestParam("username") String username){
        System.out.println(">> downvoting : " + postId + " " + username);
        this.postService.downvotePost(postId, username);
        return "redirect:/posts/";
    }
  

    @GetMapping("/deletePost")
   public String deletePost(@RequestParam("postId") Long postId){
   this.postService.deletePost(postId);
    return "redirect:/home/";
   }


   @GetMapping("/updatePost")
   public String updatePost(@RequestParam("postId") Long postId,Authentication authentication,Model model){
    Post post = this.postService.getPostById(postId);
    String username=authentication.getName();
    model.addAttribute("username", username);
    List<Subreddit> subreddits = subredditService.findAll();
    model.addAttribute("post", post);
    model.addAttribute("subreddits", subreddits);
    return "update-post";
   }



   @GetMapping("/updatepost")
public String updatePost(@RequestParam("id") Long postId,
@RequestParam("title") String title,
@RequestParam("content") String content,
@RequestParam("subreddit") String subreddit,
@RequestParam("username") String username,Model model
){
   this.postService.updatePost(postId, title, content, subreddit, username);
   Post post=this.postService.getPostById((long)postId);
   Comment comment=new Comment();
   model.addAttribute("Comment", comment);
   model.addAttribute("post",post);
   return "viewPost";
}
   
}
