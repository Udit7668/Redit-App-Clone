package com.reddit.apicontroller;

import com.reddit.entity.Post;
import com.reddit.service.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@NoArgsConstructor
public class PostControllerApi {
    @Autowired
    private PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        try {
            this.postService.addPost(post);
            return ResponseEntity.of(Optional.of(post));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
      Post post=this.postService.getPostById(id);
      if(post==null){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
        return ResponseEntity.of(Optional.of(post));
    }

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts=this.postService.getAllPosts();
        if(posts.size()<=0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(posts));

    }


}
