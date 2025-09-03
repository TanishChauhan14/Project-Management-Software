package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.GetTaskDTO;
import com.Project_Management.DTO.TaskResponseDTO;
import com.Project_Management.Models.Task;
import com.Project_Management.Services.TaskServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class TaskController {

    @Autowired
    TaskServices taskServices;

   @PostMapping("/createtask/{projectid}")
    public TaskResponseDTO createTask(@RequestBody Task task, 
                                  @PathVariable int projectid,
                                  @RequestParam int assignedToId) {
    return taskServices.createTask(projectid, assignedToId, task);
}


    @GetMapping("/getTask/{projectid}")
    public ResponseEntity<?> getTaskbyprojectid(@PathVariable int projectid){ 
        List<GetTaskDTO> Rs = taskServices.taskByProjectId(projectid);
        return ResponseEntity.ok(Rs);
    }
    
    @GetMapping("/getTaskbyid/{Taskid}")
    public ResponseEntity<?> getTaskByid(@PathVariable int Taskid){
        String rs = taskServices.getTaskbyid(Taskid);
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/getalltasks")
    public ResponseEntity<?> getalltasks(){
        List<GetTaskDTO> rs = taskServices.getalltask();
        return ResponseEntity.ok(rs);
    }

    @PutMapping("updatetask/{id}")
    public ResponseEntity<?> updatetask(@PathVariable int taskid, @RequestBody TaskResponseDTO task) {
        
        TaskResponseDTO rs = taskServices.updatetask(taskid, task);

        return ResponseEntity.ok(rs);
    }

    
    



    
}
