package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.UsersShowResponse.UserResponseDTO;
import com.Project_Management.Services.TeamdetailsServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TeamdetailsController {

    @Autowired
    private TeamdetailsServices teamdetailsServices;

    @GetMapping("getallteamdetails")
    public List<UserResponseDTO> getallteamMembers(@RequestParam(defaultValue = "1") Integer pagesize , @RequestParam(defaultValue = "0") Integer pagenumber) {

        List<UserResponseDTO> Result = teamdetailsServices.getallteamMembers(pagesize,pagenumber);

        return Result;
       
    }

    @GetMapping("getTaskDetailsBy/{id}")
    public ResponseEntity<UserResponseDTO> getteammember(@PathVariable int id){

        UserResponseDTO Result = teamdetailsServices.GetTeamMembersById(id);

        return ResponseEntity.ok(Result);
    }
    
}
