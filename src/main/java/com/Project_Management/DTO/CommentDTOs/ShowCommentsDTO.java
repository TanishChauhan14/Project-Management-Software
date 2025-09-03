package com.Project_Management.DTO.CommentDTOs;

import java.util.List;

import com.Project_Management.DTO.TaskResponseDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowCommentsDTO {

    private TaskResponseDTO task;
    private List<CommentDTO> comments;
}
