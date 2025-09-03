package com.Project_Management.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
  

    private Boolean availability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EmployeeRoleenum Employee_role;  

    @ElementCollection
    @CollectionTable(
        name = "team_member_skills",
        joinColumns = @JoinColumn(name = "team_member_id")
    )
    @Column(name = "skill")
    private List<String> skills;  

    private int leavesTaken;

    private float salary;

    private float performance;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users user;

    @ManyToOne
    @JoinColumn(name = "active_project_id")
    @JsonIgnore
    private Project activeProject;
}
