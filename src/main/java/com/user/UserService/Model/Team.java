package com.user.UserService.Model;

import com.user.UserService.Model.User.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class Team {
    @Id
    @UuidGenerator
    private UUID id;
    @OneToMany(targetEntity = User.class)
    @JoinColumn(
        name = "team_id",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "team_id")
    )
    private List<User> members;
    @OneToOne(targetEntity = User.class)
    @JoinColumn(
            name = "leader_usr_id",
            referencedColumnName = "id",
            columnDefinition = "UUID",
            foreignKey = @ForeignKey(name = "leader_usr_id")
    )
    private User leader;
}
