/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.dtos;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author austine.okoroafor
 */
@Data
public class VideoPriceDto {

    @NotEmpty(message = "username is required!")
    private String logginUser;
    @NotEmpty(message = "title is required!")
    private String title;
    @NotEmpty(message = "numberOfDays is required!")
    private int numberOfDays;
    private double regularRate;
    private double childrenMoviesRate;
    private double newRealeaseRate;

}
