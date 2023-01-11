package com.reddit.controller;

import java.io.IOException;
import java.util.List;

import com.reddit.entity.User;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import com.reddit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.service.PostService;
import com.reddit.service.SubredditService;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SubredditController {
    @Autowired
    private SubredditService subredditService;


    @Autowired
    private PostService postService;
    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private PostRepository postRepository;

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
    @GetMapping("/subreddit/add")
    public String create(Model model){
        model.addAttribute("subreddit",new Subreddit());
        return "saveSubreddit";
    }
    @PostMapping("/subreddit/addSubreddit")
    public String createSubreddit(@ModelAttribute("subreddit")Subreddit subreddit,
                                  @RequestParam("fileprofile") MultipartFile file,
                                  @RequestParam(name="username") String username,Model model) throws IOException {
        String subredditName=subreddit.getName();
         Subreddit name=this.subredditService.findByName(subredditName);
         if(name!=null){
        model.addAttribute("error", "Subreddit Already exists");
         return "saveSubreddit";
         }                       
        subredditService.createSubreddit(subreddit,username,file);
        return "redirect:/home/";
    }

    @CrossOrigin
    @GetMapping("/subreddit/showSubreddit")
    public String showSubreddit(Model model){
        List<Subreddit> subredditList= subredditService.findAll();
        System.out.println(subredditList);
        model.addAttribute("allSubreddit",subredditList);
        return "showsubreddit";
    }
    @GetMapping("/subreddit/deleteSubreddit")
    public String deleteSubreddit(@RequestParam("subredditId") Long subredditId){
        Subreddit subreddit= subredditService.findById(subredditId);
        subreddit.setAdmins(null);
        List<Post> postList=subreddit.getPosts();
        for(Post post:postList){
            postRepository.delete(post);
        }
        subreddit.setUsers(null);
        subredditRepository.delete(subreddit);
        System.out.println(" i am deleting subreddit"+subredditId);
        return "redirect:/home/";
    }
    @GetMapping("/subreddit/showSubredditId")
    public Subreddit showSubredditById(@RequestParam("subredditId")Long subredditId,Model model){
        Subreddit subreddit =subredditService.findById(subredditId);
        List<Post> postList=subreddit.getPosts();
        model.addAttribute("posts",postList);
        model.addAttribute("thesubreddits",subreddit);
        List<Subreddit> subredditList= subredditService.findAll();
        model.addAttribute("subreddits",subredditList);
        return  subreddit;
    }

    @GetMapping("/subreddit/view/{viewId}")
    public String viewAllPostBySubreddit(@PathVariable("viewId") Long subredditId,
                                         Model model,
                                         Authentication authentication){
        Subreddit subreddit = subredditService.findById(subredditId);
        return "redirect:/r/"+subreddit.getName();
    }
    @GetMapping("r/{subredditName}")
    public String viewAllPostBySubredditName(@PathVariable("subredditName") String subredditName,
                                         Model model,
                                         Authentication authentication){
        Subreddit subreddit =subredditService.findByName(subredditName);
        List<Post> posts=this.subredditService.getAllPostBySubredditName(subreddit.getId());
        model.addAttribute("posts", posts);
        List<Subreddit> subreddits=this.subredditService.findAll();
        model.addAttribute("subreddits", subreddits);
        model.addAttribute("thesubreddit",subreddit);
        model.addAttribute("followingStatus", "no");
        if(authentication!=null) {
            for (User subredditUser : subreddit.getUsers()) {
                if (subredditUser.getUsername().equals(authentication.getName())) {
                    model.addAttribute("followingStatus", "yes");
                    break;
                }
            }
            for (User subredditMods : subreddit.getAdmins()) {
                if (subredditMods.getUsername().equals(authentication.getName())) {
                    model.addAttribute("mod", "yes");
                    break;
                }
            }
        }
        System.out.println(">> subreddit name : " + subreddit.getName());
        return "subreddit-post";
    }

    @GetMapping("/subreddit/search")
    public String searchPosts(@RequestParam("subredditId") String subredditId,
                              @RequestParam("searchKey") String searchKey,
                              Authentication authentication, Model model){
        System.out.println(">> subredditId : " + subredditId + " searchKey : " + searchKey);
        Subreddit subreddit = subredditService.findById(Long.parseLong(subredditId));
        List<Post> posts = this.subredditService.getPostsByTitleOfASubreddit(Long.parseLong(subredditId),searchKey);
        List<Subreddit> subreddits = this.subredditService.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("subreddits", subreddits);
        model.addAttribute("thesubreddit", subreddit);
        model.addAttribute("followingStatus", "no");
        if(authentication!=null) {
            for (User subredditUser : subreddit.getUsers()) {
                if (subredditUser.getUsername().equals(authentication.getName())) {
                    model.addAttribute("followingStatus", "yes");
                    break;
                }
            }
        }
        System.out.println(">> subreddit name : " + subreddit.getName());
        return "subreddit-post";
    }

@GetMapping("/subreddit/newPosts/{subredditname}")
   public String newPosts(@PathVariable("subredditname") String name,
                          Authentication authentication,
                          Model model){
    List<Post> posts=this.postService.sortPost(name);
    Subreddit subreddit=this.subredditService.findByName(name);
    List<Subreddit> subreddits = this.subredditService.findAll();
    model.addAttribute("followingStatus", "no");
    if(authentication!=null){
        for (User subredditUser: subreddit.getUsers()) {
            if(subredditUser.getUsername().equals(authentication.getName())){
                model.addAttribute("followingStatus", "yes");
                break;
            }
        }
    }
    model.addAttribute("posts", posts);
    model.addAttribute("subreddits", subreddits);
    model.addAttribute("thesubreddit", subreddit);
    return "subreddit-post";
   }


   @GetMapping("/subreddit/topPosts/{subredditname}")
   public String topPosts(@PathVariable("subredditname") String name,
                          Authentication authentication,
                          Model model){
        List<Post> posts=this.postService.topPost(name);
        Subreddit subreddit=this.subredditService.findByName(name);
        List<Subreddit> subreddits = this.subredditService.findAll();
        model.addAttribute("posts", posts);
       model.addAttribute("followingStatus", "no");
       if(authentication!=null) {
           for (User subredditUser : subreddit.getUsers()) {
               if (subredditUser.getUsername().equals(authentication.getName())) {
                   model.addAttribute("followingStatus", "yes");
                   break;
               }
           }
       }
        model.addAttribute("subreddits", subreddits);
        model.addAttribute("thesubreddit", subreddit);
        return "subreddit-post";
   }


   @GetMapping("/subreddit/upvote")
   public String upVote(@RequestParam("postId") Long postId,
                        Authentication authentication,
                        Model model,@RequestParam("name") String name ){
       this.postService.upvotePost(postId, authentication.getName());
       List<Post> posts=this.postService.sortPost(name);
       Subreddit subreddit=this.subredditService.findByName(name);
       List<Subreddit> subreddits = this.subredditService.findAll();
       model.addAttribute("posts", posts);
       model.addAttribute("subreddits", subreddits);
       model.addAttribute("thesubreddit", subreddit);
       model.addAttribute("followingStatus", "no");
       if(authentication!=null) {
           for (User subredditUser : subreddit.getUsers()) {
               if (subredditUser.getUsername().equals(authentication.getName())) {
                   model.addAttribute("followingStatus", "yes");
                   break;
               }
           }
       }
       return "subreddit-post";
   }


   @GetMapping("/subreddit/downvote")
   public String downVote(@RequestParam("postId") Long postId,
                          Authentication  authentication,
                          Model model,@RequestParam("name") String name){
       this.postService.downvotePost(postId, authentication.getName());
       List<Post> posts=this.postService.sortPost(name);
       Subreddit subreddit=this.subredditService.findByName(name);
       List<Subreddit> subreddits = this.subredditService.findAll();
       model.addAttribute("posts", posts);
       model.addAttribute("subreddits", subreddits);
       model.addAttribute("thesubreddit", subreddit);
       model.addAttribute("followingStatus", "no");
       if(authentication==null) {
           for (User subredditUser : subreddit.getUsers()) {
               if (subredditUser.getUsername().equals(authentication.getName())) {
                   model.addAttribute("followingStatus", "yes");
                   break;
               }
           }
       }
       return "subreddit-post";
   }
   @PostMapping("/subreddit/follow")
   public String followSubreddit(@RequestParam("subreddit") String subredditName,
                                 Authentication authentication,
                                 Model model){
        subredditService.addFollowUser(subredditName,authentication.getName());
        return "redirect:/subreddit/view/"+subredditService.findByName(subredditName).getId();
   }
    @PostMapping("/subreddit/unfollow")
    public String unFollowSubreddit(@RequestParam("subreddit") String subredditName,
                                  Authentication authentication,
                                  Model model){
        subredditService.removeFollowUser(subredditName,authentication.getName());
        return "redirect:/subreddit/view/"+subredditService.findByName(subredditName).getId();
    }
}
