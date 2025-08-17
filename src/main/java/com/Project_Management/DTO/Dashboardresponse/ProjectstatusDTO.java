package com.Project_Management.DTO.Dashboardresponse;

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
