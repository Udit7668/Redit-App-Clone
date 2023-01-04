package com.reddit.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

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
    @Nullable
    @Lob
    private String content;
    private Integer voteCount = 0;
    @Column(name="created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedDate;


}