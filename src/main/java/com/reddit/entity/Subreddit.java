package com.reddit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Community name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdDate;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "subreddit")
    private List<Post> posts = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(name = "subreddit_users",
            joinColumns = @JoinColumn(name="subreddit_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(name = "subreddit_admins",
            joinColumns = @JoinColumn(name="subreddit_id"),
            inverseJoinColumns = @JoinColumn(name="admin_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> admins = new ArrayList<>();
}
