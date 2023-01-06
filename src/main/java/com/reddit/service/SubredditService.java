package com.reddit.service;

import com.reddit.entity.Subreddit;
import com.reddit.entity.User;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class SubredditService {
    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private UserRepository userRespository;

    public void createSubreddit(Subreddit subreddit, String username) {
        User user = userRespository.findByUsername(username).get();
        subreddit.getAdmins().add(user);
        subredditRepository.save(subreddit);
    }
    public List<Subreddit> findAll() {
        return subredditRepository.findAll();
    }
    public void deleteSubredditById(Long subredditId) {
        subredditRepository.deleteById(subredditId);
    }
    public Subreddit findById(Long subredditId) {
        return subredditRepository.findById(subredditId).get();
    }
    
    public Subreddit findByName(String subredditName){
        Subreddit subreddit=this.subredditRepository.findSubredditByName(subredditName);
        return subreddit;
    }
}
