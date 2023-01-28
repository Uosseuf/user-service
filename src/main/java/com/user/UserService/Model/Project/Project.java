package com.user.UserService.Model.Project;

import com.user.UserService.Model.User.User;
import lombok.Data;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Project {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(
            nullable = false,
            columnDefinition = "TEXT")
    private String description;
    @Transient
    private List<Task> tasks;
    @ManyToMany(mappedBy = "partOf")
    private List<User> workingOn;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_key")
    )
    private User createdBy;
    private LocalDate deadline;

    public void addTask(Task task) {
        tasks.add(task);
    }
}
