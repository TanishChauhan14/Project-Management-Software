package com.Project_Management.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    private Project Project;

    @ManyToOne
    private Users assignedto;

    public Task() {
    }

    public Task(int id, String title, String description, TaskStatus status,
            com.Project_Management.Models.Project project, Users assignedto, LocalDate duedate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        Project = project;
        this.assignedto = assignedto;
        this.duedate = duedate;
    }

    private LocalDate duedate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return Project;
    }

    public void setProject(Project project) {
        Project = project;
    }

    public Users getAssignedto() {
        return assignedto;
    }

    public void setAssignedto(Users assignedto) {
        this.assignedto = assignedto;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

}
