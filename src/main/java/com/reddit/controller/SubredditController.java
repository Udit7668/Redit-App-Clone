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
@RequestMapping("/subreddit")
public class SubredditController {
    @Autowired
    private SubredditService subredditService;

//    @GetMapping("/home/add")
//    public String create(Model model){
//        model.addAttribute("subreddit",new Subreddit());
//        return "saveSubreddit";
//<<<<<<< HEAD
//    }
//    @PostMapping("/home/addSubreddit")
//    public String createSubreddit(@ModelAttribute("subreddit")Subreddit subreddit, @RequestParam(value = "subredditId",required = false) Long subredditId){
//        subredditService.createSubreddit(subreddit,subredditId);
//        return "redirect:/home/";
//    }
//=======
//    }
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
    public Subreddit showSubredditById(@RequestParam("subredditId")Long subredditId,Model model){
        Subreddit subreddit =subredditService.findById(subredditId);
        List<Post> postList=subreddit.getPosts();
        model.addAttribute("posts",postList);
        model.addAttribute("thesubreddits",subreddit);
        List<Subreddit> subredditList= subredditService.findAll();
        model.addAttribute("subreddits",subredditList);
        return  subreddit;
    }


    @GetMapping("view/{viewId}")
    public String viewAllPostBySubreddit(@PathVariable("viewId") Long subredditId,Model model){
        Subreddit subreddit =subredditService.findById(subredditId);
        List<Post> posts=this.subredditService.getAllPostBySubredditName(subredditId);
        model.addAttribute("posts", posts);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        model.addAttribute("thesubreddit",subreddit);
        return "subreddit-post";
    }

}
