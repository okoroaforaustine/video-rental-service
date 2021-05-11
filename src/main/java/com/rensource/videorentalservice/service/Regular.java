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
public class Regular implements VideoType {

    @Override
    public double calculatePrice(int noOfDays, String x) {
        int days = ConstantsUtil.Regular_RATING;
        double totalPrice = days * noOfDays;
        return totalPrice;
    }

}
