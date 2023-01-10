package com.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reddit.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    
   @Query(value = "select * from message m where m.sender like :c and m.reciever like :d",nativeQuery = true)
   public List<Message> findBySenderAndReciver(@Param("c") String sender,@Param("d") String reciever);
    
}
