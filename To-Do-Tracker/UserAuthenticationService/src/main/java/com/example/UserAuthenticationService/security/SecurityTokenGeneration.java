package com.example.UserAuthenticationService.security;

import com.example.UserAuthenticationService.domain.User;

public interface SecurityTokenGeneration
{
    String createToken(User user);
}
