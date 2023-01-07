package com.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reddit.entity.Subreddit;
import com.reddit.service.SubredditService;
@Controller
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
    @PostMapping("/home/addSubreddit")
    public String createSubreddit(@ModelAttribute("subreddit")Subreddit subreddit,
                                  @RequestParam(name="username") String username,
                                  @RequestParam(value = "subredditId",
                                          required = false) Long subredditId){
        subredditService.createSubreddit(subreddit,subredditId,username);
        return "redirect:/home/";
    }

    @CrossOrigin
    @GetMapping("/home/showSubreddit")
    public String showSubreddit(Model model){
        List<Subreddit> subredditList= subredditService.findAll();
        System.out.println(subredditList);
        model.addAttribute("allSubreddit",subredditList);
        return "showsubreddit";
    }
    @GetMapping("/home/deleteSubreddit")
    public String deleteSubreddit(@RequestParam("subredditId") Long subredditId){
        subredditService.deleteSubredditById(subredditId);
        return "showsubreddit";
    }
    @GetMapping("/home/showSubredditId")
    public Subreddit showSubredditById(@RequestParam("subredditId")Long subredditId){
        Subreddit subreddit =subredditService.findById(subredditId);
        return  subreddit;
    }

}
