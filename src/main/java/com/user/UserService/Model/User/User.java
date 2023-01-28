package com.user.UserService.Model.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.UserService.Model.Project.Project;
import com.user.UserService.Model.Team;
import com.user.UserService.config.SecurityRoles.ApplicationRoles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "username", columnNames = "username")
})
public final class User implements UserDetails {
    @Id
    @UuidGenerator
    @NonNull
    private UUID id;
    @Column(nullable = false)
    @NonNull
    private String username;
    @Column(nullable = false)
    @NonNull
    private String firstName;
    @Column(nullable = false)
    @NonNull
    private String surname;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NonNull
    private Gender gender;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NonNull
    private String password;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NonNull
    private String email;
    @Column(nullable = false)
    @NonNull
    private LocalDate dateOfBirth;
    @OneToMany(targetEntity = Project.class)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private List<Project> createdProjects;
    @ManyToMany(targetEntity = Project.class)
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "project_id",
                    referencedColumnName = "id"
            ),
            foreignKey = @ForeignKey(name = "user_id"),
            inverseForeignKey = @ForeignKey(name = "project_id")
    )
    private List<Project> partOf;
    @OneToOne(mappedBy = "leader")
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NonNull
    private ApplicationRoles role;
    @Transient
    private Set<? extends GrantedAuthority> grantedAuthorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public static UserBuilder builder() {
        return new UserBuilder().id(UUID.randomUUID()).grantedAuthorities(new HashSet<>());
    }

    public void createProject(Project project) {
        createdProjects.add(project);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
