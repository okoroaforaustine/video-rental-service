/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.repositories;

import com.rensource.videorentalservice.entities.VideoRentalDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author austine.okoroafor
 */
@Repository
public interface VideoRentalRepository extends JpaRepository<VideoRentalDetails, Long> {

    @Query(value = "SELECT username,title,video_type,amount,created_at FROM videodb.video_rental_details WHERE username=:username  or (created_at BETWEEN :fromDate AND :toDate)", nativeQuery = true)
    public Page<VideoRentalDetails> searchCriteria(@Param("username") String username, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, Pageable pageable);

}
