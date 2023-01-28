package com.user.UserService.config.SecurityRoles;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationUserAuthorities {
    USER_READ("student:read"),
    USER_WRITE("student:write"),
    USER_DELETE("student:delete"),
    USERS_READ("students:read"),
    USERS_WRITE("students:write");
    private final String permissions;

}
