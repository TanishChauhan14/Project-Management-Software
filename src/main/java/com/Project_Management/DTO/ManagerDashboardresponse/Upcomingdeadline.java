package com.Project_Management.DTO.ManagerDashboardresponse;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Upcomingdeadline {

    private int project_id;
    private String project_name;
    private LocalDate deadline;
    private int daysremaining;
    private List<Teammembers> teammembers;
}
