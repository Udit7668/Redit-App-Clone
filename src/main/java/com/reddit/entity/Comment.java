package com.reddit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="comment")
    private String comment;
    @CreationTimestamp
    @Column(name="created_at", updatable = false, nullable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    private Timestamp updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
