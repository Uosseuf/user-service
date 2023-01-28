package com.user.UserService.Model.User;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.user.UserService.config.SecurityRoles.ApplicationRoles;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class UserRegisterRequest {
    @JsonProperty("firstName")
    @NonNull
    private String firstName;
    @JsonProperty("lastName")
    @NonNull
    private String lastName;
    @JsonProperty("username")
    @NonNull
    private String username;
    @JsonProperty("email")
    @NonNull
    private String email;
    @JsonProperty("password")
    @NonNull
    private String password;
    @JsonProperty("dateOfBirth")
    @NonNull
    private LocalDate dateOfBirth;
    @JsonProperty("gender")
    @JsonEnumDefaultValue
    private Gender gender;

    @JsonProperty("role")
    @JsonEnumDefaultValue
    private ApplicationRoles role;

}
