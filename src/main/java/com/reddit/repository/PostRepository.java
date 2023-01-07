package com.reddit.repository;

import com.reddit.entity.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    public Post findPostById(Long id);

  
    @Query(value="select * from post order by created_at desc",nativeQuery = true)
    public List<Post> sortPostByCreatedDate();
}
