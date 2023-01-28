package com.user.UserService.Service;

import com.user.UserService.DataAccess.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(@Qualifier("postgres-projects") ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
}
