package com.Project_Management.Services;


import com.Project_Management.DTO.CommentDTOs.CommentDTO;
import com.Project_Management.DTO.CommentDTOs.CommentRqt;
import com.Project_Management.DTO.CommentDTOs.ShowCommentsDTO;
import com.Project_Management.Models.Users;


public interface TaskCommentsServices {


    public CommentDTO Addcomments(int taskid, CommentRqt taskComments, Users author);

    public ShowCommentsDTO getcomments(int taskid);

}
