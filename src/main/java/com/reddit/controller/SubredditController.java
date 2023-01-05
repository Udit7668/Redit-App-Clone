package com.reddit.controller;

import com.reddit.entity.Subreddit;
import com.reddit.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
