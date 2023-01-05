package com.reddit.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    private String role="ROLE_USER";
    @Column(name="created_at",nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @OneToMany(mappedBy = "user")
    List<Comment> comments;
    private boolean enabled;
}
