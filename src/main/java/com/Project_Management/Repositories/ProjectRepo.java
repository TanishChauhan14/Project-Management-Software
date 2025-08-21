package com.Project_Management.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project_Management.Models.Project;
import com.Project_Management.Models.Projectstatus;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Integer> {

    int countByStatus(Projectstatus status);
    List<Project> findByMembers_Id(int managerId);

    List<Project> findByStatusIn(List<Projectstatus> statuses);

    long count();
}
