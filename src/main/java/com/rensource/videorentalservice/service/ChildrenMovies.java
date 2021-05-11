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
public class ChildrenMovies implements VideoType {

    @Override
    public double calculatePrice(int noOfDays, String Age) {
        int days = ConstantsUtil.CHILDREN_MOVIE;
        int maxAge = Integer.parseInt(Age);
        double totalPrice = days * noOfDays + maxAge / 2;
        return totalPrice;
    }

}
