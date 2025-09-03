package com.Project_Management.DTO.CommentDTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentRqt {
     private String comment;
     private List<Integer> mentions;
}
