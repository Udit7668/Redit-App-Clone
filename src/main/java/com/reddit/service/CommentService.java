package com.reddit.service;

import com.reddit.entity.Comment;
import com.reddit.entity.Post;
import com.reddit.entity.User;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRespository;

    public void addComment(String postId, Comment comment, String username) {
        Optional<Post> postOptional = postRepository.findById(Long.valueOf(Integer.parseInt(postId)));
        if (postOptional.isPresent()) {
            User user = userRespository.findByUsername(username).get();
            Post post = postOptional.get();
            comment.setPost(post);
            comment.setUser(user);
            commentRepository.save(comment);
        }
    }

    public void addReply(String postId, Comment comment, String username, String parentId) {
        Optional<Post> postOptional = postRepository.findById(Long.valueOf(Integer.parseInt(postId)));
        Optional<Comment> parentOptional = commentRepository.findById(Long.valueOf(Integer.parseInt(parentId)));
        if (postOptional.isPresent()) {
            if (parentOptional.isPresent()) {
                User user = userRespository.findByUsername(username).get();
                Post post = postOptional.get();
                Comment parent = parentOptional.get();
                comment.setParent(parent);
                comment.setPost(post);
                comment.setUser(user);
                commentRepository.save(comment);
            }
        }
    }

    public void updateComment(String postId, Comment comment) {
        Optional<Post> postOptional = postRepository.findById(Long.valueOf(Integer.parseInt(postId)));
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            comment.setPost(post);
            commentRepository.save(comment);
        }
    }

    public Comment getComment(String commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(Long.valueOf(Integer.parseInt(commentId)));
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            return comment;
        }
        return null;
    }

    public void deleteComment(String commentId) {
        Comment comment = getComment(commentId);
        if (comment != null) {
            comment.setPost(null);
            comment.setParent(null);
            comment.setUser(null);
            deleteChildrenComment(comment);
        }
    }
    public void deleteChildrenComment(Comment comment){
        System.out.println("comment for deletion - "+comment.getComment());
        if(comment.getChildren()!=null){
            for (Comment child : comment.getChildren()) {
                child.setPost(null);
                child.setParent(null);
                child.setUser(null);
                deleteChildrenComment(child);
            }
        }
        comment.setUpvotedUsers(null);
        comment.setDownvotedUsers(null);
        commentRepository.delete(comment);
    }

    @Transactional
    public void upvoteComment(Long commentId, String username) {
        Comment comment = null; 
        User user = null; 
        Optional<Comment> commentResult = commentRepository.findById(commentId);
        if (commentResult != null){
            comment = commentResult.get(); 
        }
        Optional <User> userResult = userRespository.findByUsername(username); 
        if (userResult != null){
            user = userResult.get(); 
        }
        if (comment != null && user != null){
            if (!comment.getUpvotedUsers().contains(user)){
                comment.getUpvotedUsers().add(user); 
                if (comment.getDownvotedUsers().contains(user)){
                    comment.getDownvotedUsers().remove(user); 
                }
            } else {
                comment.getUpvotedUsers().remove(user);
            }
        }
    }

    @Transactional
    public void downvoteComment(Long commentId, String username) {
        Comment comment = null;
        User user = null;
        Optional<Comment> commentResult = commentRepository.findById(commentId);
        if (commentResult != null) {
            comment = commentResult.get();
        }
        Optional<User> userResult = userRespository.findByUsername(username);
        if (userResult != null) {
            user = userResult.get();
        }
        if (comment != null && user != null) {
            if (!comment.getDownvotedUsers().contains(user)) {
                comment.getDownvotedUsers().add(user);
                if (comment.getUpvotedUsers().contains(user)) {
                    comment.getUpvotedUsers().remove(user);
                }
            } else {
                comment.getDownvotedUsers().remove(user);
            }
        }
    }
}
