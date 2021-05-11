/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.service;

import com.rensource.videorentalservice.entities.AppUser;
import com.rensource.videorentalservice.repositories.AppUserRepository;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author austine.okoroafor
 */
@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    AppUserRepository userRepo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials() == null ? null : authentication.getCredentials().toString();
        String activationIndicator = authentication.getDetails() == null ? null : authentication.getDetails().toString();

        if (!authentication.getAuthorities().isEmpty() && authentication.isAuthenticated()) {
            return authentication;
        }

        AppUser user = null;
        try {
            user = userRepo.findByUsername(username);
        } catch (ServiceException ex) {
            throw new AuthenticationCredentialsNotFoundException(ex.getMessage());
        }
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("Invalid username or password");
        }

        if (!user.getUsername().equals(username)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), Collections.EMPTY_LIST);

    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }

}
