package com.reddit.repository;

import com.reddit.entity.Post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    public Post findPostById(Long id);

  
    @Query(value="select * from post order by created_at desc",nativeQuery = true)
    public List<Post> sortPostByCreatedDate();


    @Query(value="select * from post order by vote_count desc",nativeQuery = true)
    public List<Post> sortPostByVoteCount();

    @Query(value = "select p.* from post p,subreddit s where p.subreddit_id=s.id and s.name like :c",nativeQuery = true)
    public List<Post> findPostBySubredditName(@Param("c") String subredditName);


    @Query(value = "select distinct p.* from post p,subreddit s where p.subreddit_id=s.id and s.name like :c or p.title like :c or p.content like :c",nativeQuery = true)
    public List<Post> findPostBySubredditNameOrPostTitle(@Param("c") String subredditName);


    @Query(value = "select distinct p.* from post p,users u where p.id=u.id and u.username like :c",nativeQuery = true)
    public List<Post> findPostByUser(@Param("c") String username);

    public Page<Post> findAll(Pageable pageable);

}
