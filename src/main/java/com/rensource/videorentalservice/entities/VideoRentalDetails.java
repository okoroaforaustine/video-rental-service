/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.entities;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Column;
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
@Table(name = "video_rental_details")
public class VideoRentalDetails extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty(message = "username  is required!")
    @ApiModelProperty(notes = "username")
    private String username;
    @NotEmpty(message = " title is required!")
    @ApiModelProperty(notes = "title")
    private String title;
    @NotEmpty(message = "video type is required!")
    @ApiModelProperty(notes = "video Type")
    @Column(name = "video_type")
    private String videoType;
    @NotNull(message = "amount is required!")
    @ApiModelProperty(notes = "amount")
    private double amount;

}
