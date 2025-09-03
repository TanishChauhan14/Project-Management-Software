package com.Project_Management.Services;

import java.util.List;

import com.Project_Management.DTO.UsersShowResponse.UserResponseDTO;

public interface TeamdetailsServices {

    List<UserResponseDTO> getallteamMembers(int pagesize, int pagenumber);

    UserResponseDTO GetTeamMembersById(int id);

}
