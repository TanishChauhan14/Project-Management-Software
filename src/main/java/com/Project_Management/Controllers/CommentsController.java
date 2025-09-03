package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.CommentDTOs.CommentDTO;
import com.Project_Management.DTO.CommentDTOs.CommentRqt;
import com.Project_Management.DTO.CommentDTOs.ShowCommentsDTO;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.TaskCommentsServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CommentsController {

    @Autowired
    private TaskCommentsServices taskCommentsServices;

    @Autowired
    private UsersAuthRepo userRepo;

    @PostMapping("addcommentontask/{id}")
    public ResponseEntity<?> Addcomments(@PathVariable int id, @RequestBody CommentRqt taskComments) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users author = userRepo.findByUsername(username);

        CommentDTO Result = taskCommentsServices.Addcomments(id, taskComments, author);

        return ResponseEntity.ok(Result);
    }

    @GetMapping("Getcomments/{id}")
    public ResponseEntity<?> getcommentbytask(@PathVariable int id) {

        ShowCommentsDTO Response = taskCommentsServices.getcomments(id);

        return ResponseEntity.ok(Response);
    }

}
