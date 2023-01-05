package com.reddit.controller;

import com.reddit.entity.Post;
import com.reddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private PostService postService;
    @RequestMapping("/")

    public String getAllPosts(Model model){
        List<Post> posts=this.postService.getAllPosts();
        model.addAttribute("posts",posts);
        return "home";
    }

    @GetMapping("/search")
    public String search()
    {
  return null;
    }
    @GetMapping("/login")
    public String login(){
        return "redirect:/form/login";
    }

    @GetMapping("/register")
    public String register(){
        return "redirect:/form/register";
    }

    @GetMapping("/newPosts")
    public String filterPostByNewPosts(Model model){
   List<Post> posts=this.postService.sortPostByDate(); 
   model.addAttribute("posts", posts);
   return "home";
    }
    


}
