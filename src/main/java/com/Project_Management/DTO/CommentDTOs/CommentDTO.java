package com.Project_Management.DTO.CommentDTOs;

import java.time.LocalDateTime;
import java.util.List;

import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDTO {

    private int comment_id;
    private String comment;
    private Teammembers author;
    private List<Teammembers> mentions;
    private LocalDateTime commentedAt;
}
