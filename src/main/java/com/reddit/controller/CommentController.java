package com.reddit.controller;

import com.reddit.entity.Comment;
import com.reddit.repository.PostRepository;
import com.reddit.service.CommentService;
import com.reddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public String addComment(@RequestParam(name="postId") String postId,
                                    @ModelAttribute("comment") Comment comment,
                                    Model model){
        commentService.addComment(postId,comment);
        return "test";//To Do - redirect to the reddit post
    }
    @PostMapping("/updateComment")
    public String updateComment(@RequestParam(name="commentId") String commentId,Model model){
        Comment comment = commentService.getComment(commentId);
        model.addAttribute("comment", comment);
        return "update-comment";
    }
    @PostMapping("/processUpdateComment")//To Do - Get the logged in user id or Username and get the user object and add it to the comment while saving
    public String processUpdateComment(@ModelAttribute(name="postId") String postId,
                                       @ModelAttribute("comment") Comment comment,
                                       Model model){
        commentService.addComment(postId,comment);
        return "test";//redirect to the reddit post
    }
    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam(name="commentId") String commentId,
                                Model model) {
        commentService.deleteComment(commentId);

        return "test";//To Do - redirect to the reddit post
    }
}
