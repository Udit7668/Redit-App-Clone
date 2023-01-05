package com.reddit.controller;

import com.reddit.entity.Subreddit;
import com.reddit.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public class SubredditController {
    @Autowired
    private SubredditService subredditService;

    public String createSubreddit(@ModelAttribute("subreddit")Subreddit subreddit, @RequestParam("subredditId") Long subredditId){
        subredditService.createSubreddit(subreddit,subredditId);
       return "";
    }
}
