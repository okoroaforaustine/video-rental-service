/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author austine.okoroafor
 */
@Entity
@Data
@Table(name = "Video")
public class Video extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    @NotEmpty(message = "video title is required!")
    @ApiModelProperty(notes = "Video title")
    private String title;
    @NotEmpty(message = "type title is required!")
    @ApiModelProperty(notes = "Video Type")
    private String type;
    @ApiModelProperty(notes = "Max Age")
    @Column(name = "max_age")
    private String maxAge;
    @ApiModelProperty(notes = "Release Year")
    @Column(name = "release_year")
    private String releaseYear;
    @ApiModelProperty(notes = "List Of Genre")
    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Genre> genre;

}
