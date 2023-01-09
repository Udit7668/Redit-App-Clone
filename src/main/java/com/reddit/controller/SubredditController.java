package com.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.service.PostService;
import com.reddit.service.SubredditService;
@Controller
@RequestMapping("/subreddit")
public class SubredditController {
    @Autowired
    private SubredditService subredditService;


    @Autowired
    private PostService postService;

//    @GetMapping("/home/add")
//    public String create(Model model){
//        model.addAttribute("subreddit",new Subreddit());
//        return "saveSubreddit";
//<<<<<<< HEAD
//    }
//    @PostMapping("/home/addSubreddit")
//    public String createSubreddit(@ModelAttribute("subreddit")Subreddit subreddit, @RequestParam(value = "subredditId",required = false) Long subredditId){
//        subredditService.createSubreddit(subreddit,subredditId);
//        return "redirect:/home/";
//    }
//=======
//    }
    @GetMapping("/add")
    public String create(Model model){
        model.addAttribute("subreddit",new Subreddit());
        return "saveSubreddit";
    }
    @PostMapping("/addSubreddit")
    public String createSubreddit(@ModelAttribute("subreddit")Subreddit subreddit,
                                  @RequestParam(name="username") String username,Model model){
        String subredditName=subreddit.getName();
         Subreddit name=this.subredditService.findByName(subredditName);
         if(name!=null){
        model.addAttribute("error", "Subreddit Already exists");
         return "saveSubreddit";
         }                       
        subredditService.createSubreddit(subreddit,username);
        return "redirect:/home/";
    }

    @CrossOrigin
    @GetMapping("/showSubreddit")
    public String showSubreddit(Model model){
        List<Subreddit> subredditList= subredditService.findAll();
        System.out.println(subredditList);
        model.addAttribute("allSubreddit",subredditList);
        return "showsubreddit";
    }
    @GetMapping("/deleteSubreddit")
    public String deleteSubreddit(@RequestParam("subredditId") Long subredditId){
        subredditService.deleteSubredditById(subredditId);
        return "showsubreddit";
    }
    @GetMapping("/showSubredditId")
    public Subreddit showSubredditById(@RequestParam("subredditId")Long subredditId,Model model){
        Subreddit subreddit =subredditService.findById(subredditId);
        List<Post> postList=subreddit.getPosts();
        model.addAttribute("posts",postList);
        model.addAttribute("thesubreddits",subreddit);
        List<Subreddit> subredditList= subredditService.findAll();
        model.addAttribute("subreddits",subredditList);
        return  subreddit;
    }

    @GetMapping("view/{viewId}")
    public String viewAllPostBySubreddit(@PathVariable("viewId") Long subredditId,Model model){
        Subreddit subreddit =subredditService.findById(subredditId);
        List<Post> posts=this.subredditService.getAllPostBySubredditName(subredditId);
        model.addAttribute("posts", posts);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        model.addAttribute("thesubreddit",subreddit);
        System.out.println(">> subreddit name : " + subreddit.getName());
        return "subreddit-post";
    }

    @GetMapping("/search")
    public String searchPosts(@RequestParam("subredditId") String subredditId, @RequestParam("searchKey") String searchKey, Model model){
        System.out.println(">> subredditId : " + subredditId + " searchKey : " + searchKey);
        Subreddit subreddit = subredditService.findById(Long.parseLong(subredditId));
        List<Post> posts = this.subredditService.getPostsByTitleOfASubreddit(Long.parseLong(subredditId),searchKey);
        List<Subreddit> subreddits = this.subredditService.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("subreddits", subreddits);
        model.addAttribute("thesubreddit", subreddit);
        System.out.println(">> subreddit name : " + subreddit.getName());
        return "subreddit-post";
    }

@GetMapping("/newPosts/{subredditname}")
   public String newPosts(@PathVariable("subredditname") String name,Model model){
    List<Post> posts=this.postService.sortPost(name);
    Subreddit subreddit=this.subredditService.findByName(name);
    List<Subreddit> subreddits = this.subredditService.findAll();
    model.addAttribute("posts", posts);
    model.addAttribute("subreddits", subreddits);
    model.addAttribute("thesubreddit", subreddit);
    return "subreddit-post";
   }


   @GetMapping("/topPosts/{subredditname}")
   public String topPosts(@PathVariable("subredditname") String name,Model model){
    List<Post> posts=this.postService.topPost(name);
    Subreddit subreddit=this.subredditService.findByName(name);
    List<Subreddit> subreddits = this.subredditService.findAll();
    model.addAttribute("posts", posts);
    model.addAttribute("subreddits", subreddits);
    model.addAttribute("thesubreddit", subreddit);
    return "subreddit-post";
   }


   @GetMapping("/upvote")
   public String upVote(@RequestParam("postId") Long postId, @RequestParam("username") String username,Model model,@RequestParam("name") String name ){
       System.out.println(">> upvoting : " + postId + " " + username);
       this.postService.upvotePost(postId, username); 
       List<Post> posts=this.postService.sortPost(name);
       Subreddit subreddit=this.subredditService.findByName(name);
       List<Subreddit> subreddits = this.subredditService.findAll();
       model.addAttribute("posts", posts);
       model.addAttribute("subreddits", subreddits);
       model.addAttribute("thesubreddit", subreddit);
       return "subreddit-post";
   }


   @GetMapping("/downvote")
   public String downVote(@RequestParam("postId") Long postId, @RequestParam("username") String username,Model model,@RequestParam("name") String name ){
       System.out.println(">> upvoting : " + postId + " " + username);
       this.postService.downvotePost(postId, username); 
       List<Post> posts=this.postService.sortPost(name);
       Subreddit subreddit=this.subredditService.findByName(name);
       List<Subreddit> subreddits = this.subredditService.findAll();
       model.addAttribute("posts", posts);
       model.addAttribute("subreddits", subreddits);
       model.addAttribute("thesubreddit", subreddit);
       return "subreddit-post";
   }
     
}
