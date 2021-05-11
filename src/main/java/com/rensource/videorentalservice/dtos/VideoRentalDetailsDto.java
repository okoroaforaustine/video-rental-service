/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.dtos;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author austine.okoroafor
 */
@Data
public class VideoRentalDetailsDto {

    private String username;
    private String title;
    private String videoType;
    private double amount;
    private Date createdDate;

}
