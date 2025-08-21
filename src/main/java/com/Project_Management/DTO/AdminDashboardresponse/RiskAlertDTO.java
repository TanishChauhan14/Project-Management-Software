package com.Project_Management.DTO.AdminDashboardresponse;

import java.time.Instant;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RiskAlertDTO {

    private int projectmanagerid;
    private int projectid;
    private String risk_level;
    private Instant predictioncompletion;

}
