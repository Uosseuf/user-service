package com.user.UserService.Model.Project;

import com.user.UserService.Model.User.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class Task {
    private UUID id;
    private String taskTitle;
    private User issuedBy;
    private List<User> assigendTo;
    private double donePercentage;
    private LocalDate deadline;
}
