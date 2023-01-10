package com.reddit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.reddit.entity.Message;
import com.reddit.entity.User;
import com.reddit.service.MessageService;
import com.reddit.service.UserService;

@Controller
public class MessageController {


    @Autowired
    private UserService userService;

    @Autowired  
    private MessageService messageService;
   
    @GetMapping("/message")
    public String sendMessage(Model model,@RequestParam("user") String user,@RequestParam("loginUser") String loginUser) {
      List<Message> messages=this.messageService.getAllMessage(user, loginUser);
      model.addAttribute("messages",messages);  
      model.addAttribute("user", user);
        model.addAttribute("loginUser", loginUser);
       return "message";
    }

    @GetMapping("/chat/{reciever}")
    public String recieveMessage(@PathVariable("reciever") String reciever,Model model,@RequestParam("message") String message,@RequestParam("loginUser") String loginUser){
      this.messageService.addMessage(reciever, loginUser, message); 
      List<Message> messages=this.messageService.getAllMessage(reciever, loginUser);
      model.addAttribute("messages",messages);
      model.addAttribute("user", reciever);
      model.addAttribute("loginUser", loginUser);
      return "message";
    }
}