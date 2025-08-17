package com.Project_Management.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project_Management.Models.Activity;

@Repository
public interface ActivityRepo extends JpaRepository<Activity,Long> {

    List<Activity> findTop5ByOrderByTimestampDesc();
}
