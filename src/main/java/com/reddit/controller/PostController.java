package com.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reddit.entity.Comment;
import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.service.PostService;
import com.reddit.service.SubredditService;

import ch.qos.logback.core.net.SyslogOutputStream;

import javax.transaction.Transactional;


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

    @RequestMapping("/savepost")
    public String saveOrUpdatePost(@RequestParam("title") String title,@RequestParam("content") String content,
    @RequestParam("subreddit") String subreddit,@RequestParam("username") String username
    ){
         
        this.postService.addPost(title,content,subreddit,username);
        return "redirect:/posts/";
    }

    @GetMapping("/")
    public String showAllPost(Model model){
    List<Post> posts=this.postService.getAllPost();
    model.addAttribute("posts",posts);
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
}
