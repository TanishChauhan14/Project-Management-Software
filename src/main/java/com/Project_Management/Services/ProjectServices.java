package com.Project_Management.Services;

import java.util.List;

import com.Project_Management.DTO.ProjectRequestDTO;
import com.Project_Management.DTO.ProjectTimeline;
import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.Models.Project;

public interface ProjectServices {

    public Project createProject(ProjectRequestDTO dto, String username);

    public List<MyProjectDTO> getallproject();

    List<MyProjectDTO> getallprojectpagination(int pagesize, int pagenumber);
 
    String getprojectbyid(int id);

    public Boolean delete(int id);

    public String updateproject(MyProjectDTO projectdetails);

    public ProjectTimeline projecttimeline(int id);
}
