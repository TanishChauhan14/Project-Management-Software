package com.Project_Management.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project_Management.Models.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task,Integer> {

}
