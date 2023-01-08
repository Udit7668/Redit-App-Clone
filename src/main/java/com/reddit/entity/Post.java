package com.reddit.entity;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Title cannot be empty or Null")
    private String title;
    private String image;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(name="vote_count")
    private Integer voteCount = 0;
    @Column(name="created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedDate;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subreddit_id")
    private Subreddit subreddit;
    @OneToMany(fetch = LAZY,mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments;
}