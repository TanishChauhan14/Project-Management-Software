package com.Project_Management.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "users") 
@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false) 
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

     @Enumerated(EnumType.STRING) 
    @Column(nullable = false, length = 20)
    private UserRole role; 

    @OneToOne
    @JoinColumn(name = "refresh_token_token_id") 
    @JsonIgnore
    private RefreshToken refreshToken;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
