/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.controller;

import com.rensource.videorentalservice.entities.AppUser;
import com.rensource.videorentalservice.repositories.AppUserRepository;
import com.rensource.videorentalservice.util.AppUtil;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rensource.videorentalservice.dtos.Error;
import com.rensource.videorentalservice.dtos.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author austine.okoroafor
 */
@RestController
@RequestMapping("/User")
@Api(value = "User", description = "All API operations on User...")
@Slf4j
@CrossOrigin
@RequiredArgsConstructor

public class UserController {

    private final AppUserRepository userRepo;
    @Autowired
    AppUtil appUtils;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ApiOperation(value = "Add new User",
            notes = "Add new user in the system, provided all information are correct",
            response = Response.class)
    public ResponseEntity<?> saveUser(@Valid @RequestBody AppUser user, @ApiIgnore Errors errors) {
        log.debug("Received request to register new merchant.");

        if (errors.hasErrors()) {
            return appUtils.returnPostValidationErrors(errors);
        }

        List<Error> validationErrors = validateUser(user);
        if (!validationErrors.isEmpty()) {
            return appUtils.returnErrorResponse(validationErrors, HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        log.debug("New writer registered successfully. Returning response...");

        return appUtils.returnSuccessResponse(user, "User created Succesfully");

    }

    private List<Error> validateUser(AppUser user) {
        List<Error> errors = new ArrayList<Error>();

        log.debug("Validating User request...");

        AppUser existingName = userRepo.findByUsername(user.getUsername());
        if (null != existingName && !existingName.getId().equals(user.getId())) {
            errors.add(new Error("user with this name '"
                    + user.getUsername() + "' already exists.", 4, "username"));
        }

        AppUser existEmail = userRepo.findByEmail(user.getEmail());
        if (null != existEmail) {
            errors.add(new Error("user with this email '"
                    + user.getEmail() + "' already exists.", 4, "email"));
        }
        log.debug("User registration request validation completed...");

        return errors;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestHeader String authorization) throws ServiceException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //String token = userService.requestToken(auth.getName());

        return ResponseEntity.ok(auth.getName());
    }

}
