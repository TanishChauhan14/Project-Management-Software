package com.Project_Management.Models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "CommentsOnTasks")
@Entity
public class TaskComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Users author;

    @ManyToMany
    @JoinTable(
        name = "comment_mentions",
        joinColumns = @JoinColumn(name = "comment_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> mentions;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime commentedAt;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @PrePersist
    protected void onCreate() {
        this.commentedAt = LocalDateTime.now();
    }
}
