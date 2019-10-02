package com.acorn.tutorial.gateway.authentication.localauth;

import lombok.Data;

import java.util.List;

/**
 * Represents the entry for a user in the local user directory.
 */
@Data
public class LocalUser {
    private String userId;
    private String password;
    private List<String> roles;
}
