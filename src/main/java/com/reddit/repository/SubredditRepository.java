package com.reddit.repository;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    @Query(value="select * from subreddit where name like :c",nativeQuery = true)
    public Subreddit findSubredditByName(@Param("c") String subredditName);

}
