package com.Project_Management.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project_Management.Models.Users;
import com.Project_Management.Models.UserRole;



@Repository
public interface UsersAuthRepo extends JpaRepository<Users,Integer> {

    public Users findByUsername(String username);
    public Users findByEmail(String email);
    public long countByRole(UserRole role);
    public List<Users> findByIsActiveTrue();
}
