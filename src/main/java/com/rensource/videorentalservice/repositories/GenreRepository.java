/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.repositories;

import com.rensource.videorentalservice.entities.AppUser;
import com.rensource.videorentalservice.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author austine.okoroafor
 */
@Service
public interface GenreRepository extends JpaRepository<Genre, String> {

}
