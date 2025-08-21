package com.Project_Management.Services;

import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Users;

public interface ActivityServices {

    void addingrecentactivity(ActivityType type, Users user);
}
