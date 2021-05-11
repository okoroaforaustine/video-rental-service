/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.entities;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author austine.okoroafor
 */
@Entity
@Data
@Table(name = "VideoPrice")
public class VideoPrice extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    @ApiModelProperty(notes = "title")
    @NotEmpty(message = "title is required!")
    private String title;
    @ApiModelProperty(notes = "Number of days is required")
    @NotNull(message = "numberOfDays is required!")
    private int numberOfDays;
    private double regularPriceRate;
    private double childrenMoviesRate;
    private double newRealeaseRate;
    @ApiModelProperty(notes = "login User is required")
    @NotEmpty(message = "login User!")
    private String logginUser;

}
