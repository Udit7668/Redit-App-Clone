package com.reddit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private String name;
    @Nullable
    private String description;

    private Instant createdDate;

//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Post> posts;



}
