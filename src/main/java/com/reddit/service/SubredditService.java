package com.reddit.service;

import com.reddit.entity.Subreddit;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubredditService {
    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private PostRepository postRepository;

    public void createSubreddit(Subreddit subreddit, Long subredditId) {
       subredditRepository.save(subreddit);
    }
}
