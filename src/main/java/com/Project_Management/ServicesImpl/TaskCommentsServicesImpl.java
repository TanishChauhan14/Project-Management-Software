package com.Project_Management.ServicesImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.TaskResponseDTO;
import com.Project_Management.DTO.CommentDTOs.CommentDTO;
import com.Project_Management.DTO.CommentDTOs.CommentRqt;
import com.Project_Management.DTO.CommentDTOs.ShowCommentsDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;
import com.Project_Management.Models.Task;
import com.Project_Management.Models.TaskComments;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.TaskRepo;
import com.Project_Management.Repositories.TaskcommentsRepo;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.TaskCommentsServices;

@Service
public class TaskCommentsServicesImpl implements TaskCommentsServices {

    @Autowired
    private TaskcommentsRepo commentRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UsersAuthRepo usersAuthRepo;

    @Override
    public CommentDTO Addcomments(int taskid, CommentRqt taskComments, Users author) {
        TaskComments comments = new TaskComments();

        Optional<Task> task = taskRepo.findById(taskid);

        comments.setComment(taskComments.getComment());
        comments.setAuthor(author);
        if (task.isPresent()) {
            comments.setTask(task.get());
        }
        List<Users> Mentions = getmentionsuser(taskComments);
        comments.setMentions(Mentions);

        commentRepo.save(comments);

        // Making Response
        CommentDTO taskCommentDTO = new CommentDTO();

        taskCommentDTO.setComment(comments.getComment());

        taskCommentDTO.setCommentedAt(comments.getCommentedAt());

        taskCommentDTO.setComment_id(comments.getId());

        taskCommentDTO.setAuthor(new Teammembers(
                author.getId(),
                author.getUsername(),
                author.getEmail()));

        // Task dbtask = task.get();
        // taskCommentDTO.setTask(new TaskResponseDTO(
        // dbtask.getId(),
        // dbtask.getTitle(),
        // dbtask.getStatus(),
        // dbtask.getAssignedTo().getUsername(),
        // dbtask.getProject().getName()));

        List<Teammembers> mentionslist = Mentions
                .stream()
                .map(m -> new Teammembers(
                        m.getId(),
                        m.getUsername(),
                        m.getEmail()))
                .toList();

        taskCommentDTO.setMentions(mentionslist);

        return taskCommentDTO;
    }

    private List<Users> getmentionsuser(CommentRqt taskComments) {
        if (taskComments.getMentions() == null || taskComments.getMentions().isEmpty()) {
            return Collections.emptyList();
        }
        return usersAuthRepo.findAllById(taskComments.getMentions());
    }

    @Override
    public ShowCommentsDTO getcomments(int taskid) {

        Optional<Task> optask = taskRepo.findById(taskid);
        ShowCommentsDTO Rs = new ShowCommentsDTO();

        if (optask.isPresent()) {

            Task dbtask = optask.get();

            // TASK DTO
            TaskResponseDTO taskdto = new TaskResponseDTO(
                    dbtask.getId(),
                    dbtask.getTitle(),
                    dbtask.getStatus(),
                    dbtask.getAssignedTo().getUsername(),
                    dbtask.getProject().getName());

            // COMMENTS
            List<CommentDTO> commentDTOs = dbtask.getComments()
                    .stream()
                    .map(c -> new CommentDTO(
                            c.getId(),
                            c.getComment(),
                            new Teammembers(
                                    c.getAuthor().getId(),
                                    c.getAuthor().getUsername(),
                                    c.getAuthor().getEmail()),
                            c.getMentions()
                                    .stream()
                                    .map(mu -> new Teammembers(
                                            mu.getId(),
                                            mu.getUsername(),
                                            mu.getEmail()))
                                    .toList(),
                            c.getCommentedAt()))
                    .toList();

            Rs.setTask(taskdto);
            Rs.setComments(commentDTOs);

            return Rs;

        } else {
            throw new RuntimeException("Task Not Found ");

        }

    }

}
