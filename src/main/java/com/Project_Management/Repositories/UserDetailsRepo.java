package com.Project_Management.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Project_Management.Models.Users;


@Repository
public interface UserDetailsRepo extends JpaRepository<Users,Integer> {

    public Users findByUsername(String username);
}
