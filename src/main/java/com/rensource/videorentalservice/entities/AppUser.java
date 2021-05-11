/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author austine.okoroafor
 */
@Entity
@Data
@Table(name = "User")
public class AppUser extends AuditModel {

    @Id
    @GeneratedValue(generator = "uuid2")

    private Long id;
    @NotEmpty(message = "user Name is required!")
    @ApiModelProperty(notes = "username")
    private String username;
    @ApiModelProperty(notes = "Password")
    @NotEmpty(message = "password is required!")
    private String password;
    @ApiModelProperty(notes = "firstname")
    @NotEmpty(message = "FirstName is required!")
    private String firstname;
    @ApiModelProperty(notes = "last name")
    @NotEmpty(message = "LastName is required!")
    private String lastname;
    @ApiModelProperty(notes = "mobile number")
    @NotEmpty(message = "Mobile is required!")
    private String mobile;
    @ApiModelProperty(notes = "email")
    @NotEmpty(message = "email is required!")
    private String email;

}
