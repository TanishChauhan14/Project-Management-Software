package com.Project_Management.DTO.AdminDashboardresponse;

import java.time.Instant;

import com.Project_Management.Models.ActivityType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecentActivitiesDTO {

    private Long id;
    @Enumerated(EnumType.STRING)
    private ActivityType type;   
    private String user;
    private String message;
    private Instant timestamp;
}
