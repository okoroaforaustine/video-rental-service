/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.repositories;

import com.rensource.videorentalservice.entities.VideoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author austine.okoroafor
 */
@Repository
public interface VideoPriceRepository extends JpaRepository<VideoPrice, String> {

}
