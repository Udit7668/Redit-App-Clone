package com.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reddit.entity.Subreddit;
import com.reddit.service.SubredditService;
@Controller
public class SubredditController {
    @Autowired
    private SubredditService subredditService;

    @PostMapping("/addSubreddit")
    public String createSubreddit(@ModelAttribute("subreddit")Subreddit subreddit, @RequestParam(value = "subredditId",required = false) Long subredditId){
        subredditService.createSubreddit(subreddit,subredditId);
       return "";
    }
    @GetMapping("/showSubreddit")
    public List<Subreddit> showSubreddit(){
       return subredditService.findAll();

    }

}
