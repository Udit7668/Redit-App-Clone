package com.reddit.service;

import com.reddit.entity.Post;
import com.reddit.entity.Subreddit;
import com.reddit.entity.User;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class SubredditService {
    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private UserRepository userRespository;
    @Autowired
    private PostRepository postRepository;

    public void createSubreddit(Subreddit subreddit, Long subredditId) {
        subreddit.setCreatedDate(Timestamp.from(Instant.now()));

    }
    public void createSubreddit(Subreddit subreddit, Long subredditId, String username) {
        subreddit.setCreatedDate(Timestamp.from(Instant.now()));
    }

    public void createSubreddit(Subreddit subreddit, String username, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            System.out.println("File is empty");
        }
        else{
            subreddit.setImage(file.getOriginalFilename());

            System.out.println(new ClassPathResource("").getFile().getAbsolutePath());
            System.out.println("///////////////////////");
            File saveFile= new ClassPathResource("static/img").getFile();
            Path path=  Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }
        User user = userRespository.findByUsername(username).get();
        subreddit.getAdmins().add(user);
        subredditRepository.save(subreddit);
    }
    public List<Subreddit> findAll() {
        return subredditRepository.findAll();
    }
    public void deleteSubredditById(Long subredditId) {
        subredditRepository.deleteById(subredditId);

    }
    public Subreddit findById(Long subredditId) {
        return subredditRepository.findById(subredditId).get();
    }

    
    public Subreddit findByName(String subredditName){
        Subreddit subreddit=this.subredditRepository.findSubredditByName(subredditName);
        return subreddit;
    }


    public List<Post> getAllPostBySubredditName(Long subredditId){
        Optional<Subreddit> subredditOptional=this.subredditRepository.findById(subredditId);
        Subreddit subreddit=subredditOptional.get();
        String subredditName=subreddit.getName();
       List<Post> posts=this.postRepository.findPostBySubredditName(subredditName); 
        return posts;
    }

    public List<Post> getPostsByTitleOfASubreddit(long subredditId, String searchKey) {
        List<Post> posts = this.postRepository.findPostsByTitleWithCommonSubreddit(subredditId, searchKey); 
        return posts; 
    }

    public void addFollowUser(String subredditName, String name) {
        Subreddit subreddit = subredditRepository.findSubredditByName(subredditName);
        subreddit.getUsers().add(userRespository.getByUsername(name));
        subredditRepository.save(subreddit);
    }

    public void removeFollowUser(String subredditName, String name) {
        Subreddit subreddit = subredditRepository.findSubredditByName(subredditName);
        subreddit.getUsers().remove(userRespository.getByUsername(name));
        subredditRepository.save(subreddit);
    }
}
