package com.Project_Management.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project_Management.Models.Project;
import com.Project_Management.Models.Projectstatus;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Integer> {

    int countByStatus(Projectstatus status);

    long count();
}
