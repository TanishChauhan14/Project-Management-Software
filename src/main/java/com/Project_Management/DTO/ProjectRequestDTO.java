package com.Project_Management.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ProjectRequestDTO {

    private String name;
    private String description;
    private Integer ownerId;
    private List<Integer> members;  
    private LocalDate deliverydate;
    private List<String> clientrequirement;
    private String levelofproject;   
    private String referencewebsite;
    private boolean approveddesign;
    private String status; 
}
