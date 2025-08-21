package com.Project_Management.Services;

import com.Project_Management.DTO.ProjectRequestDTO;
import com.Project_Management.Models.Project;

public interface ProjectServices {

    public Project createProject(ProjectRequestDTO dto, String username);
}
