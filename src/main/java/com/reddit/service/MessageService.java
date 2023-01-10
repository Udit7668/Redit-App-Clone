package com.reddit.service;

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
}
