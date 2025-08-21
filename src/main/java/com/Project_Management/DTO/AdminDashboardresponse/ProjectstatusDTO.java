package com.Project_Management.DTO.AdminDashboardresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectstatusDTO {

    private int active;
    private int completed;
    private int at_risk;
    private int critical;
}
