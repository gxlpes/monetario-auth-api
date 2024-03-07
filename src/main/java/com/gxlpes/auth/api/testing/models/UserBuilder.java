package com.gxlpes.auth.api.testing.models;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBuilder {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private List<Token> tokens;

    public UserBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public UserBuilder setTokens(List<Token> tokens) {
        this.tokens = tokens;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(this.id);
        user.setFirstname(this.firstname);
        user.setLastname(this.lastname);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setRole(this.role);
        user.setTokens(this.tokens);
        return user;
    }
}
