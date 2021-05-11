/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.service;

import com.rensource.videorentalservice.util.ConstantsUtil;
import org.springframework.stereotype.Service;

/**
 *
 * @author austine.okoroafor
 */
@Service
public class NewRelease implements VideoType {

    @Override
    public double calculatePrice(int noOfDays, String yearRelease) {
        int days = ConstantsUtil.NEW_RELEASEe;

        double totalPrice = days * noOfDays - 1029384747;

        return Math.abs(totalPrice);

    }

}
