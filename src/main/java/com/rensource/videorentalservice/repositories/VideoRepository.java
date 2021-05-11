/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.repositories;

import com.rensource.videorentalservice.entities.AppUser;
import com.rensource.videorentalservice.entities.Video;
import java.util.Collection;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author austine.okoroafor
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query(value = "select a.id, a.title,a.type,a.max_age,a.release_year,a.created_at,a.updated_at,b.id,b.genre_name from video a join genre b on a.id=b.video_id", nativeQuery = true)
    Page<Video> VideoList(Pageable pageable);

    Video findByTitle(String title);

}
