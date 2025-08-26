package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.ProjectRequestDTO;
import com.Project_Management.DTO.ProjectTimeline;
import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.Models.Project;
import com.Project_Management.Services.ProjectServices;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class ProjectController {


    @Autowired
    ProjectServices projectServices;



    @PostMapping("createproject")
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequestDTO dto) {
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Project project = projectServices.createProject(dto, username);
        return ResponseEntity.ok(project);      
    }

    // Get All Projects
    @GetMapping("/getallproject") 
    public ResponseEntity<?> getallproject(){

        List<MyProjectDTO> rs = projectServices.getallproject();

        return ResponseEntity.ok(rs);
    }

    // Get All Project By pagination 

    @GetMapping("/getallprojectpagination") 
    public ResponseEntity<?> getallprojectpagiantion(@RequestParam(defaultValue = "1") Integer pagesize , @RequestParam(defaultValue = "0") Integer pagenumber){

        List<MyProjectDTO> rs = projectServices.getallprojectpagination(pagesize, pagenumber);

        return ResponseEntity.ok(rs);
    }

    @GetMapping("/getproject/{id}")
    public ResponseEntity<?> getprojectbyid(@PathVariable int id) {
        return ResponseEntity.ok(projectServices.getprojectbyid(id));
    }
  
    // Remove a project.

    @DeleteMapping("/removeproject/{id}")
    public ResponseEntity<?> removeproject(@PathVariable int id) {
        Boolean rs = projectServices.delete(id);

        if (rs) {
            return ResponseEntity.ok("Project is remove with id :- "+id);
        }

        throw new RuntimeErrorException(null, "Not Found");
    }

    // Update Project

    @PutMapping("/updateproject")
    public ResponseEntity<?> updateproject(@RequestBody MyProjectDTO projectDTO) {
        
        String rs = projectServices.updateproject(projectDTO);
        if(rs.startsWith("Error")){
            throw new RuntimeException("Error in Updation of :- "+projectDTO.getProjecttitle()+" "+projectDTO.getProjectid());
        }

        return ResponseEntity.ok(rs);


    }

    // Get Project Deadline

    @GetMapping("getprojectdeadline/{id}")
    public ResponseEntity<?> getprojectTimeline(@PathVariable int id){

        ProjectTimeline projectTimeline = projectServices.projecttimeline(id);

        if(projectTimeline != null){
            return ResponseEntity.ok(projectTimeline);
        }

        throw new RuntimeException("Project Not Found ");
    }


    
}
