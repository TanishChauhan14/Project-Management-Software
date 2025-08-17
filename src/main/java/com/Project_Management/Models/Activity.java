package com.Project_Management.Models;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityType type;   

    private String user;         

    @Column(columnDefinition = "TEXT")
    private String message;      

    private Instant timestamp;  

      @PrePersist
    protected void onCreate() {
        this.timestamp = Instant.now();
    }
}

