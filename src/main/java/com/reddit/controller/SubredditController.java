package com.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.service.SubredditService;
@Controller
@RequestMapping("subreddit")
public class SubredditController {
    @Autowired
    private SubredditService subredditService;

    @GetMapping("/add")
    public String create(Model model){
        model.addAttribute("subreddit",new Subreddit());
        return "saveSubreddit";
    }
    @PostMapping("/addSubreddit")
    public String createSubreddit(@ModelAttribute("subreddit")Subreddit subreddit,
                                  @RequestParam(name="username") String username){
        subredditService.createSubreddit(subreddit,username);
        return "redirect:/home/";
    }
    @CrossOrigin
    @GetMapping("/showSubreddit")
    public String showSubreddit(Model model){
        List<Subreddit> subredditList= subredditService.findAll();
        System.out.println(subredditList);
        model.addAttribute("allSubreddit",subredditList);
        return "showsubreddit";
    }
    @GetMapping("/deleteSubreddit")
    public String deleteSubreddit(@RequestParam("subredditId") Long subredditId){
        subredditService.deleteSubredditById(subredditId);
        return "showsubreddit";
    }
    @GetMapping("/showSubredditId")
    public Subreddit showSubredditById(@RequestParam("subredditId")Long subredditId){
        Subreddit subreddit =subredditService.findById(subredditId);
        return  subreddit;
    }


    @GetMapping("view/{viewId}")
    public String viewAllPostBySubreddit(@PathVariable("viewId") Long subredditId,Model model){
        List<Post> posts=this.subredditService.getAllPostBySubredditName(subredditId);
        model.addAttribute("posts", posts);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        return "subreddit-post";
    }

}
