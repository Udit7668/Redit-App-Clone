package com.reddit.apicontroller;

import com.reddit.entity.Post;
import com.reddit.service.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@NoArgsConstructor
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        this.postService.addPost(post);
        return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        this.postService.getPostById(id);
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts=this.postService.getAllPosts();
        return null;
    }


}
