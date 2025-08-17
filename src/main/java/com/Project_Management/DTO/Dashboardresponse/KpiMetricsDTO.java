package com.Project_Management.DTO.Dashboardresponse;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KpiMetricsDTO {

    private int totalactiveprojects;
    private int totalclients;
    private double teamUtilization;
    private Long monthlyrevenue;

}
