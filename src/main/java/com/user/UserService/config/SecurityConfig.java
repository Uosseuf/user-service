package com.user.UserService.config;

import com.user.UserService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.user.UserService.config.SecurityRoles.ApplicationRoles.*;
import static com.user.UserService.config.SecurityRoles.ApplicationUserAuthorities.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/","index", "/css/*", "/js/*").permitAll()
                .requestMatchers("/templates/css/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/user/auth/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/user/get").hasAuthority(USERS_READ.getPermissions())
                .requestMatchers(HttpMethod.DELETE,"/api/v1/user/delete/*").hasAuthority(USER_DELETE.getPermissions())
                .requestMatchers(HttpMethod.GET,"/api/v1/user/get/*").hasAuthority(USER_READ.getPermissions())
                .requestMatchers("/api/v1/*").hasAnyRole(USER.name(),ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities(ADMIN.getGrantedAuthority())
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .authorities(USER.getGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(
                admin,
                user
        );
    }

}
