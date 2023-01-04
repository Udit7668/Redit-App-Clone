package com.reddit.service;

import com.reddit.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubredditService {
    @Autowired
    private SubredditRepository subredditRepository;
}
