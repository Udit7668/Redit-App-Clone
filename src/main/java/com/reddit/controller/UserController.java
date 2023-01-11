package com.reddit.controller;

import java.util.List;

import com.reddit.entity.Subreddit;
import com.reddit.service.CommentService;
import com.reddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import com.reddit.entity.Comment;
import com.reddit.entity.Post;
import com.reddit.entity.User;
import com.reddit.service.PostService;
import com.reddit.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @RequestMapping("user/{username}")
    public String getUserProfile(@PathVariable String username,
                                 Model model){
        List<Post> userPosts = userService.getPosts(username);
      List<User> users=this.userService.findAllUser();
         model.addAttribute("users", users);
        List<Comment> userComments = userService.getComments(username);
        model.addAttribute("posts",userPosts);
        model.addAttribute("userProfile",username);
        model.addAttribute("comments",userComments);
        return "user-profile";
    }
    @RequestMapping("/userSearch")
    public String getUserProfileSearch(@RequestParam("profileId") String username,
                                       @RequestParam("search") String search,
                                       Model model){
        List<Post> userPosts = userService.getSearchPosts(username,search);
        model.addAttribute("posts",userPosts);
        model.addAttribute("userProfile",username);
        model.addAttribute("username",username);
        return "user-profile";
    }
    @GetMapping("user/newPosts/{username}")
    public String filterPostByNewPosts(@PathVariable("username") String username,Model model){
        List<Post> posts=this.userService.getSortPostByDate(username);
        model.addAttribute("posts", posts);
        model.addAttribute("userProfile",username);
        return "user-profile";
    }


    @PostMapping("user/topPosts/{username}")
    public String sortPostByVoteCount(@PathVariable("username") String username,Model model){
        List<Post> posts=this.userService.getSortPostByVoteCount(username);
        model.addAttribute("posts",posts);
        model.addAttribute("userProfile",username);
        return "user-profile";
    }
    @PostMapping("user/post/upvote")
    public String upVote(@RequestParam("postId") Long postId,
                         @RequestParam("profileId") String profileId,
                         @RequestParam("username") String username ){
        this.postService.upvotePost(postId, username);
        return "redirect:/user/"+profileId;
    }

    @PostMapping("user/post/downvote")
    public String downVote(@RequestParam("postId") Long postId,
                           @RequestParam("profileId") String profileId,
                           @RequestParam("username") String username){
        this.postService.downvotePost(postId, username);
        return "redirect:/user/"+profileId;
    }
    @PostMapping("user/comment/upvote")
    public String upVoteComment(@RequestParam("commentId") Long commentId,
                                @RequestParam("profileId") String profileId,
                                Authentication authentication){
        String username = authentication.getName();
        this.commentService.upvoteComment(commentId, username);
        return "redirect:/user/"+profileId;
    }
    @PostMapping("user/comment/downvote")
    public String downVoteComment(@RequestParam("commentId") Long commentId,
                                  @RequestParam("profileId") String profileId,
                                  Authentication authentication){
        String username = authentication.getName();
        this.commentService.downvoteComment(commentId, username);
        return "redirect:/user/"+profileId;
    }

}
