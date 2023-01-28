package com.user.UserService.Controller;

import com.user.UserService.Exception.UserExceptions.RegisterExceptions.NotEnoughAuthorityException;
import com.user.UserService.Model.User.User;
import com.user.UserService.Model.User.UserRegisterRequest;
import com.user.UserService.Service.UserService;
import com.user.UserService.config.SecurityRoles.ApplicationRoles;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService service;

    @Autowired

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public int registerUser(@RequestBody UserRegisterRequest userRequest, HttpServletRequest request) {
        if(userRequest.getRole().equals(ApplicationRoles.ADMIN))
            if(!request.isUserInRole(ApplicationRoles.ADMIN.name()))
                throw new NotEnoughAuthorityException();
        return service.insertUser(userRequest);
    }

    @PostMapping("/auth/login")
    public User loginUser(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        return service.loginUser(email, password);
    }

    @GetMapping("/get/{id}")
    public Optional<User> getUserById(@PathVariable("id") UUID id) {
        return service.selectUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public int deleteUserById(@PathVariable("id") UUID id) {
        return service.deleteUserById(id);
    }
    @GetMapping("/get")
    public List<User> selectAllUsers() {
        return service.selectAllUsers();
    }
}
