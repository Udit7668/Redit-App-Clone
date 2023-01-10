package com.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reddit.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    
}
