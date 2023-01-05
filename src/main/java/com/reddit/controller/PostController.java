package com.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String createNewPost(Model model){
        Post post = new Post();
       List<Subreddit> subreddits = subredditService.findAll();
        model.addAttribute("post", post);
       model.addAttribute("subreddits", subreddits);
        return "new-post";
    }

    @RequestMapping("/savepost")
    public String saveOrUpdatePost(@RequestParam("title") String title,@RequestParam("content") String content,
    @RequestParam("subreddit") String subreddit
    ){
        this.postService.addPost(title,content,subreddit);
        return "";
    }

    @GetMapping("")
    public String viewPost(@PathVariable("id") int postId, Model model){
        Post post  = postService.getPostById((long)postId);
        return "view-post";
    }
}
