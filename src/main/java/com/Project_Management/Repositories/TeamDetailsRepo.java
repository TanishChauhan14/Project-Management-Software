package com.Project_Management.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project_Management.Models.TeamDetails;

@Repository
public interface TeamDetailsRepo extends JpaRepository<TeamDetails,Integer> {


}
