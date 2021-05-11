/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.repositories;

import com.rensource.videorentalservice.entities.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author austine.okoroafor
 */
@Service
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    public AppUser findByUsername(String username);

    public AppUser findByPassword(String password);

    public AppUser findByEmail(String email);

}
