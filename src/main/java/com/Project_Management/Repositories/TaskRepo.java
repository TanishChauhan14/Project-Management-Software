package com.Project_Management.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project_Management.Models.Task;
import com.Project_Management.Models.TaskStatus;

@Repository
public interface TaskRepo extends JpaRepository<Task,Integer> {


    int countByStatus(TaskStatus toDo);

    List<Task> findByAssignedTo_Id(int employeeid);

    List<Task> findByProject_Id(int projectId);

}
