package com.Project_Management.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Users> members;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime startdate;

    private LocalDate deliverydate;

    private List<String> clientrequirement;

    @Column(nullable = false,updatable = true)
    @Enumerated(EnumType.STRING)
    private rankproject levelofproject;

    @Column(nullable = true,updatable = false)
    private String referencewebsite;

    private Boolean approveddesign = false;

    @Enumerated(EnumType.STRING)
    private Projectstatus status;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
