package com.user.UserService.DataAccess;

import com.user.UserService.Model.Project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("postgres-projects")
public interface ProjectRepository extends JpaRepository<Project, UUID> {

}
