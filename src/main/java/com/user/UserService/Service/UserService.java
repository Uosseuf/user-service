package com.user.UserService.Service;

import com.user.UserService.DataAccess.ProjectRepository;
import com.user.UserService.DataAccess.UserRepository;
import com.user.UserService.Model.Project.Project;
import com.user.UserService.Model.Team;
import com.user.UserService.Model.User.User;
import com.user.UserService.Model.User.UserRegisterRequest;
import com.user.UserService.Validation.UserValidation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository accessLayer;
    private final ProjectRepository projectRepository;

    public UserService(PasswordEncoder passwordEncoder, @Qualifier("postgres-users") UserRepository accessLayer,
                       @Qualifier("postgres-projects") ProjectRepository projectRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accessLayer = accessLayer;
        this.projectRepository = projectRepository;
    }

    private User completeUser(User user) {
        //TODO create logic for completing logic
        return user;
    }

    public int insertUser(UserRegisterRequest user) {
        User userToInsert = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .role(user.getRole())
                .grantedAuthorities(user.getRole().getGrantedAuthority())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .firstName(user.getFirstName())
                .surname(user.getLastName())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .build();

        UserValidation.validateUser(userToInsert);
        accessLayer.save(userToInsert);
        log.info("saved user: {}!", userToInsert);
        return 1;
    }

    public List<User> selectAllUsers() {
        log.info("Retrieving all users");
        List<User> users = accessLayer.findAll();
        users.forEach(user -> user.setGrantedAuthorities(user.getRole().getGrantedAuthority()));
        return accessLayer.findAll();
    }
    public Optional<User> selectUserById(UUID id) {
        UserValidation.validateId(id);
        User user = accessLayer.findById(id).get();
        user.setGrantedAuthorities(user.getRole().getGrantedAuthority());
        return Optional.of(user);
    }

    public int deleteUserById(UUID id) {
        UserValidation.validateId(id);
        accessLayer.deleteById(id);
        log.info("Deleted User with id {}", id);
        return 1;
    }

    public User loginUser(String email, String password) {
        UserValidation.validateCredentials(email, password);
        log.info("Validating user with email: {}, password: {} successful", email, password);
        return accessLayer.findByEmail(email).get();
    }
    public int updateUserById(UUID id, User updatedUser) {
        UserValidation.validateId(id);
        UserValidation.validateUser(updatedUser);
        User targetedUser = selectUserById(id).get();

        return 1;
    }

    public int createProject(Project project, UUID userId) {
        return 0;
    }

    public int joinTeam(UUID userId, Team team) {
        return 0;
    }

}
