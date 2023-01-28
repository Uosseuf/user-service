package com.user.UserService.config.SecurityRoles;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.user.UserService.config.SecurityRoles.ApplicationUserAuthorities.*;

@Getter
public enum ApplicationRoles {
    ADMIN(Sets.newHashSet(USER_WRITE, USER_READ, USERS_READ, USERS_WRITE, USER_DELETE)),
    USER(Sets.newHashSet(USER_WRITE, USER_READ));
    private final Set<ApplicationUserAuthorities> permissions;

    ApplicationRoles(Set<ApplicationUserAuthorities> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return permissions;
    }
}
