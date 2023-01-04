package com.reddit.service;

import com.reddit.entity.Comment;
import com.reddit.entity.Post;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public void addComment(String postId, Comment comment){
        Optional<Post> postOptional = postRepository.findById(Long.valueOf(Integer.parseInt(postId)));
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            comment.setPost(post);
            commentRepository.save(comment);
        }
    }
    public Comment getComment(String commentId){
        Optional<Comment> commentOptional = commentRepository.findById(Long.valueOf(Integer.parseInt(commentId)));
        if(commentOptional.isPresent()){
            Comment comment = commentOptional.get();
            return comment;
        }
        return null;
    }
    public void deleteComment(String commentId){
        Comment comment = getComment(commentId);
        if(comment!=null){
            comment.setPost(null);
            commentRepository.delete(comment);
        }
    }
}
