package com.Project_Management.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Project_Management.Models.RefreshToken;
import com.Project_Management.Models.Users;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {

        Optional<RefreshToken> findByRefreshToken(String refreshToken);

        Optional<RefreshToken> findByUsersUsername(String username);

        Long deleteByUsers(Users users);

        // Boolean deleteByRefreshToken(String RefreshToken);

}
