package com.Project_Management.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;

    @ManyToMany
@JoinTable(
    name = "project_members",
    joinColumns = @JoinColumn(name = "project_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
)
private List<Users> members = new ArrayList<>();


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime startdate;

    private LocalDate deliverydate;

    @ElementCollection
    @CollectionTable(name = "project_requirements", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "requirement")
    private List<String> clientrequirement;

    @Enumerated(EnumType.STRING)
    private rankproject levelofproject;

    private String referencewebsite;

    private Boolean approveddesign = false;

    @Enumerated(EnumType.STRING)
    private Projectstatus status;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
