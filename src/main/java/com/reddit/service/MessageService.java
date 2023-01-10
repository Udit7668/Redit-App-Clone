package com.reddit.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reddit.entity.Message;
import com.reddit.repository.MessageRepository;

@Service
public class MessageService {
    

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public void addMessage(String reciever,String sender,String message){
        Message msg=new Message();
        msg.setReciever(reciever);
        msg.setSender(sender);
        msg.setMessage(message);
        this.messageRepository.save(msg);
    }


    public List<Message> getAllMessage(String reciever,String sender){
        List<Message> messagesSender=this.messageRepository.findBySenderAndReciver(sender, reciever);
        List<Message> messagesReciver=this.messageRepository.findBySenderAndReciver(reciever,sender);
    
        Set<Message> hashSet=new HashSet<>();
        List<Message> messages=new ArrayList<>();
        for(Message msg:messagesSender){
            hashSet.add(msg);
        }
        for(Message msg:messagesReciver){
            hashSet.add(msg);
        }

      messages=new ArrayList<>(hashSet);
      messages.sort(Comparator.comparing(Message::getMessageDate));
   return messages;

    }
}
