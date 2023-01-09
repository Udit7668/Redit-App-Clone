package com.reddit.apicontroller;

import com.reddit.entity.Subreddit;
import com.reddit.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class SubredditControllerApi {
    @Autowired
    private SubredditService subredditService;
    @PostMapping("/api/addSubreddit")
    public ResponseEntity createSubreddit(@RequestBody Subreddit subreddit, @RequestParam(value = "subredditId",required = false) Long subredditId){
        subredditService.createSubreddit(subreddit,subredditId);
        return  new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/api/showSubreddit")
    public ResponseEntity<List<Subreddit>> showSubreddit(){
        List<Subreddit> subredditList =subredditService.findAll();
        return  new ResponseEntity<List<Subreddit>>(subredditList,HttpStatus.OK);
    }
    @GetMapping("/api/showSubredditId")
    public Subreddit showSubredditById(@RequestParam("subredditId")Long subredditId){
        Subreddit subreddit =subredditService.findById(subredditId);
        return  subreddit;
    }
    @DeleteMapping("/api/deleteSubreddit")
    public ResponseEntity deleteSubredditById(@RequestParam("subredditId")Long subredditId){
        subredditService.deleteSubredditById(subredditId);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
