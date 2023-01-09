package com.reddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reddit.entity.Comment;
import com.reddit.service.CommentService;
import com.reddit.service.PostService;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/addComment")
    public String addComment(@RequestParam(name = "postId") String postId,
            @RequestParam(name = "username") String username,
            @ModelAttribute("Comment") Comment comment,
            Model model) {
        commentService.addComment(postId, comment, username);
        return "redirect:/posts/view/" + postId;
    }

    @PostMapping("/addReplyComment")
    public String addReplyComment(@RequestParam(name = "parentId") String parentId,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "postId") String postId,
            Model model) {
        model.addAttribute("username", username);
        model.addAttribute("postId", postId);
        model.addAttribute("parentId", parentId);
        Comment comment = new Comment();
        model.addAttribute("Comment", comment);
        return "add-reply";
    }

    @PostMapping("/updateComment")
    public String updateComment(@RequestParam(name = "commentId") String commentId, Model model) {
        Comment comment = commentService.getComment(commentId);
        model.addAttribute("Comment", comment);
        return "update-comment";
    }

    @PostMapping("/processUpdateComment")
    public String processUpdateComment(@RequestParam(name = "username") String username,
            @RequestParam(name = "postId") String postId,
            @ModelAttribute("Comment") Comment comment,
            Model model) {
        commentService.addComment(postId, comment, username);
        return "redirect:/posts/view/" + postId;
    }

    @PostMapping("/updateReply")
    public String updateReply(@RequestParam(name = "commentId") String commentId,
            @RequestParam(name = "parentId") String parentId,
            Model model) {
        Comment comment = commentService.getComment(commentId);
        model.addAttribute("Comment", comment);
        model.addAttribute("parentId", parentId);
        return "update-reply";
    }

    @PostMapping("/addReply")
    public String addReply(@RequestParam(name = "username") String username,
            @RequestParam(name = "postId") String postId,
            @RequestParam(name = "parentId") String parentId,
            @ModelAttribute("Comment") Comment comment,
            Model model) {
        commentService.addReply(postId, comment, username, parentId);
        return "redirect:/posts/view/" + postId;
    }

    @PostMapping("/processUpdateReply")
    public String processUpdateReply(@RequestParam(name = "username") String username,
            @RequestParam(name = "postId") String postId,
            @RequestParam(name = "parentId") String parentId,
            @ModelAttribute("Comment") Comment comment,
            Model model) {
        commentService.addReply(postId, comment, username, parentId);
        return "redirect:/posts/view/" + postId;
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam(name = "postId") String postId,
            @RequestParam(name = "commentId") String commentId,
            Model model) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/view/" + postId;
    }

    @PostMapping("/upvoteComment")
    public String upvoteComment(@RequestParam("commentId") Long commentId, @RequestParam("username") String username, @RequestParam("postId") Long postId) {
        System.out.println(">> upvoting comment : " + commentId + " " + username + " " + postId + "\n\n");
        this.commentService.upvoteComment(commentId, username);
        return "redirect:/posts/view/" + postId;
    }

    @PostMapping("/downvoteComment")
    public String downvoteComment(@RequestParam("commentId") Long commentId, @RequestParam("username") String username, @RequestParam("postId") Long postId) {
        System.out.println(">> downvoting comment : " + commentId + " " + username + " " + postId);
        this.commentService.downvoteComment(commentId, username); 
        return "redirect:/posts/view/" + postId;
    }

    @PostMapping("/upvotePost")
    public String upvotePost(@RequestParam("username") String username, @RequestParam("postId") Long postId){
        this.postService.upvotePost(postId, username);
        return "redirect:/posts/view/" + postId;
    }

    @PostMapping("/downvotePost")
    public String downvotePost(@RequestParam("username") String username, @RequestParam("postId") Long postId) {
        this.postService.downvotePost(postId, username);
        return "redirect:/posts/view/" + postId;
    }
}
