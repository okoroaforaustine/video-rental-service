/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.dtos;

import com.rensource.videorentalservice.entities.Genre;
import java.util.List;
import lombok.Data;

/**
 *
 * @author austine.okoroafor
 */
@Data
public class VideoDto {

    private String title;
    private String type;
    private String maxAge;
    private String releaseYear;
    private List<Genre> genre;
    private String loggedInUser;

}
