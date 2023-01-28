package com.user.UserService.Validation;

import com.user.UserService.DataAccess.UserRepository;
import com.user.UserService.Exception.UserExceptions.LoginExceptions.UserNotFoundException;
import com.user.UserService.Exception.UserExceptions.RegisterExceptions.*;
import com.user.UserService.Model.User.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Log4j2
@Component
public class UserValidation {
    private static UserRepository accessLayer;
    private static PasswordEncoder passwordEncoder;
    @Autowired
    public UserValidation(UserRepository accessLayer, PasswordEncoder passwordEncoder) {
        this.accessLayer = accessLayer;
        this.passwordEncoder = passwordEncoder;
    }

    public static int validateUser(User user) {
        if(user.getEmail() == null ||
                user.getPassword() == null ||
                user.getFirstName() == null ||
                user.getGender() == null ||
                user.getSurname() == null ||
                user.getDateOfBirth() == null || user.getUsername() == null)
        {
            log.error("Failed to save user {} because One or more fields are missing", user);
            throw new MissingRequiredFieldsException("Missing One or more required fields");
        }
        if(accessLayer.findByUsername(user.getUsername()).isPresent())
            throw new UsernameAlreadyExistsException();
        if(accessLayer.findByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExistsException();
        validateEmail(user.getEmail());
        return 1;
    }

    public static int validateId(UUID id) {
        if(accessLayer.findById(id).isEmpty()) {
            log.error("Failed to validate id {} - User not found", id);
            throw new UserNotFoundException();
        }
        return 0;
    }

    public static int validateCredentials(String email, String password) {
        Optional<User> userToLogin = accessLayer.findByEmail(email);
        if(userToLogin.isPresent()) {
            userToLogin.get().setGrantedAuthorities(userToLogin.get().getRole().getGrantedAuthority());
            if(passwordEncoder.matches(password,userToLogin.get().getPassword()))
                return 1;
        }
        log.info("Validating User with email: {}, password: {} failed", email, password);
        throw new InvalidCredentialsException();
    }

    public static void validateUsername(String username) {
        accessLayer.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public static int validateEmail(String email) {
        String emailRegEx = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegEx);

        if(pattern.matcher(email).matches())
            return 1;
        log.error("failed to validate user : email not valid");
        throw new EmailNotValidException();
    }
}
