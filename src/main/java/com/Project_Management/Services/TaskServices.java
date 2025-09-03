package com.Project_Management.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Project_Management.DTO.GetTaskDTO;
import com.Project_Management.DTO.TaskResponseDTO;

import com.Project_Management.Models.Task;

@Service
public interface TaskServices {

    public TaskResponseDTO createTask(int projectid, int assignedid, Task taskdata);

    public List<GetTaskDTO> taskByProjectId(int project_id);

    public String getTaskbyid(int taskid);

    public List<GetTaskDTO> getalltask();

    public TaskResponseDTO updatetask(int id, TaskResponseDTO task);

}
