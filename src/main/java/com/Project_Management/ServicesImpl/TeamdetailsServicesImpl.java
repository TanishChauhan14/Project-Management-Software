package com.Project_Management.ServicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;
import com.Project_Management.DTO.UsersShowResponse.UserResponseDTO;
import com.Project_Management.Models.TeamDetails;
import com.Project_Management.Repositories.TeamDetailsRepo;
import com.Project_Management.Services.TeamdetailsServices;

@Service
public class TeamdetailsServicesImpl implements TeamdetailsServices {

    @Autowired
    private TeamDetailsRepo teamDetailsRepo;

    @Override
    public List<UserResponseDTO> getallteamMembers(int pagesize, int pagenumber) {

        Pageable page = PageRequest.of(pagenumber, pagesize);
        Page<TeamDetails> pages = teamDetailsRepo.findAll(page);

        List<TeamDetails> rs = pages.getContent();

        List<UserResponseDTO> TeamDetails = rs.stream()
                .map(td -> new UserResponseDTO(
                        td.getId(),
                        new Teammembers(
                                td.getUser().getId(),
                                td.getUser().getUsername(),
                                td.getUser().getEmail()),
                        td.getAvailability(),
                        td.getUser().getAvatar(),
                        td.getUser().isActive(),
                        td.getUser().getCreatedAt().toLocalDate(),
                        td.getActiveProject() == null ? null
                                : new MyProjectDTO(
                                        td.getActiveProject().getId(),
                                        td.getActiveProject().getName(),
                                        td.getActiveProject().getOwner() != null
                                                ? td.getActiveProject().getOwner().getUsername()
                                                : null,
                                        td.getActiveProject().getStatus(),
                                        td.getActiveProject().getDeliverydate(),
                                        td.getActiveProject().getMembers() != null ? td.getActiveProject().getMembers()
                                                .stream()
                                                .map(m -> new Teammembers(m.getId(), m.getUsername(), m.getEmail()))
                                                .toList() : List.of()),
                        td.getAddress(),
                        td.getEmployee_role(),
                        td.getUser().getRole(),
                        td.getLeavesTaken(),
                        td.getPerformance(),
                        td.getSalary(),
                        td.getSkills()))
                .toList();

        return TeamDetails;

    }


    @Override
    public UserResponseDTO GetTeamMembersById(int id) {
        
        UserResponseDTO TeamMemberDetails = new UserResponseDTO();

        Optional<TeamDetails> opteamdetails = teamDetailsRepo.findById(id);

        if(opteamdetails.isPresent()){
                TeamDetails teamdetails = opteamdetails.get();

                TeamMemberDetails.setUser_id(teamdetails.getId());


                TeamMemberDetails.setUser_details(new Teammembers(
                        teamdetails.getUser().getId(),
                        teamdetails.getUser().getUsername(),
                        teamdetails.getUser().getEmail()
                ));

                TeamMemberDetails.setAvailability(teamdetails.getAvailability());

                TeamMemberDetails.setAvatar(teamdetails.getUser().getAvatar());

                TeamMemberDetails.setStatus(teamdetails.getUser().isActive());

                TeamMemberDetails.setJoined_at(teamdetails.getUser().getCreatedAt().toLocalDate());

                TeamMemberDetails.setActive_project(teamdetails.getActiveProject() == null ? null : new MyProjectDTO(
                        teamdetails.getActiveProject().getId(),

                        teamdetails.getActiveProject().getName(),

                        teamdetails.getActiveProject().getOwner().getUsername(),

                        teamdetails.getActiveProject().getStatus(),

                        teamdetails.getActiveProject().getDeliverydate(),

                        teamdetails.getActiveProject().getMembers().stream().map(tm -> new Teammembers(
                                tm.getId(),
                                tm.getUsername(),
                                tm.getEmail()
                        )).toList()

                ));


                TeamMemberDetails.setAddress(teamdetails.getAddress());

                TeamMemberDetails.setEmp_role(teamdetails.getEmployee_role());

                TeamMemberDetails.setUser_role(teamdetails.getUser().getRole());

                TeamMemberDetails.setTotalleaves_taken(teamdetails.getLeavesTaken());

                TeamMemberDetails.setPerformance(teamdetails.getPerformance());

                TeamMemberDetails.setSalary(teamdetails.getSalary());

                TeamMemberDetails.setSkills(teamdetails.getSkills());

                
        }

         return TeamMemberDetails;

       
    }

}
