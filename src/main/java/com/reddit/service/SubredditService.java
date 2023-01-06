package com.reddit.service;

import com.reddit.entity.Subreddit;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class SubredditService {
    @Autowired
    private SubredditRepository subredditRepository;


    public void createSubreddit(Subreddit subreddit, Long subredditId) {
        subreddit.setCreatedDate(Timestamp.from(Instant.now()));

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
}
