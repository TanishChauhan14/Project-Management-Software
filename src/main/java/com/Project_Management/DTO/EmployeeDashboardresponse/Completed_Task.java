package com.Project_Management.DTO.EmployeeDashboardresponse;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Completed_Task {

    private int Totaltaskcompleted;
    private int Taskcurrentlyworking;
    private int TaskToDo ;

}
