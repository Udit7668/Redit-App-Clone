package com.reddit.repository;

import com.reddit.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
}
