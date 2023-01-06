package com.reddit.controller;

import com.reddit.entity.Comment;
import com.reddit.entity.Post;
import com.reddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/user/{username}")
    public String getUserProfile(@PathVariable String username,
                                 Model model){
        List<Post> userPosts = userService.getPosts(username);
        List<Comment> userComments = userService.getComments(username);
        model.addAttribute("posts",userPosts);
        model.addAttribute("comments",userComments);
        return "user-profile";
    }
}
