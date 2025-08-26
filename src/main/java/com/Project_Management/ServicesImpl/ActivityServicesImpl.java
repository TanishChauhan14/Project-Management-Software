package com.Project_Management.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.Models.Activity;
import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.ActivityRepo;
import com.Project_Management.Services.ActivityServices;

@Service
public class ActivityServicesImpl implements ActivityServices {

    @Autowired
    private ActivityRepo activityRepo;

    @Override
    public void addingrecentactivity(ActivityType type, Users user) {
        Activity activity = new Activity();
        String message = "";
        if (type == ActivityType.PROJECT_CREATED || type == ActivityType.TASK_ASSIGNED || type == ActivityType.PROJECT_UPDATED) {
            message = type + " is Done by " + user.getUsername() + ".ID is " + user.getId() + "and email "
                    + user.getEmail();
        } else if (type == ActivityType.USER_ADDED) {
            message = type + " Information :- " + user;
        } else if (type == ActivityType.USER_REMOVED || type == ActivityType.USER_UPDATED || type == ActivityType.PROJECT_DELETED) {
            message = type + " By :- username=" + user.getUsername()
                    + ", id=" + user.getId()
                    + ", email=" + user.getEmail();
        }

        activity.setType(type);
        activity.setMessage(message);
        activity.setUser(user.getUsername());

        activityRepo.save(activity);

    }
}
