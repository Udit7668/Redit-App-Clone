package com.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.service.PostService;
import com.reddit.service.SubredditService;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private PostService postService;

    @Autowired
    private SubredditService subredditService;
    
    @RequestMapping("/")
    public String getAllPosts(Model model){
        List<Post> posts=this.postService.getAllPosts();
        model.addAttribute("posts",posts);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        return "home";
    }

   @GetMapping("/login")
    public String login(){
        return "redirect:/form/login";
    }

    @GetMapping("/register")
    public String register(){
        return "redirect:/form/register";
    }

    @GetMapping("/newPosts")
    public String filterPostByNewPosts(Model model){
   List<Post> posts=this.postService.sortPostByDate(); 
   model.addAttribute("posts", posts);
   List<Subreddit> subreddits=this.subredditService.findAll();
   model.addAttribute("subreddits", subreddits);
   return "home";
    }


    @GetMapping("/topPosts")
    public String sortPostByVoteCount(Model model){
        List<Post> posts=this.postService.sortPostByVoteCount();
        model.addAttribute("posts",posts);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        return "home";
    }

    @GetMapping("/filtertoppost")
    public String sortPostByVoteCountAndSearch(Model model,@RequestParam("postId") String postId){
        List<Post> posts=this.postService.sortPostByVoteCount(postId);
        model.addAttribute("posts",posts);
        String postid = "";
        for (Post post : posts) {
            postid = postid + String.valueOf(post.getId()) + ",";
        }
        model.addAttribute("postId", postid);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        return "search-dashboard";
    }
    @GetMapping("/filternewpost")
    public String sortPostByNewPostAndSearch(Model model,@RequestParam("postId") String postId){
        List<Post> posts=this.postService.sortPostByDate(postId);
        model.addAttribute("posts",posts);
        String postid = "";
        for (Post post : posts) {
            postid = postid + String.valueOf(post.getId()) + ",";
        }
        model.addAttribute("postId", postid);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        return "search-dashboard";
    }

   
    @GetMapping("/searchpost")
    public String searchPosts(Model model,@RequestParam("search") String searchBy){
        List<Post> posts=this.postService.searchPost(searchBy);
        String postid = "";
        for (Post post : posts) {
            postid = postid + String.valueOf(post.getId()) + ",";
        }
        model.addAttribute("postId", postid);

        model.addAttribute("posts",posts);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        return "search-dashboard";
    }


}
