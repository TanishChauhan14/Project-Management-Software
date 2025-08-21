package com.Project_Management.DTO.EmployeeDashboardresponse;

import java.util.List;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDashboardDTO {

    private List<MyTodayTask> mytodaytask;
    
    private Completed_Task TaskMetrics;

}

