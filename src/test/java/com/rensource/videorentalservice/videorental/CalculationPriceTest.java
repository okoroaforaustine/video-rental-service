/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.videorental;

import com.rensource.videorentalservice.entities.VideoPrice;
import com.rensource.videorentalservice.service.VideoType;
import com.rensource.videorentalservice.service.VideoTypeFactory;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author austine.okoroafor
 */
public class CalculationPriceTest {

    @Autowired
    VideoType videoType;

    @Test
    public void calculatePrice() {

        VideoPrice videoCal = new VideoPrice();

        videoCal.setLogginUser("austine");
        videoCal.setTitle("olorodo");
        videoCal.setNumberOfDays(15);
        String maxAge = "20";

        VideoTypeFactory factory = new VideoTypeFactory();
        VideoType videoType = null;

        videoType = factory.CreateVideoType("Regular");

        double regularPrice = videoType.calculatePrice(videoCal.getNumberOfDays(), "");

        videoType = factory.CreateVideoType("ChildrenMovies");

        double childrenPrice = videoType.calculatePrice(videoCal.getNumberOfDays(), maxAge);

        videoType = factory.CreateVideoType("NewRelease");

        double NewReleasePrice = videoType.calculatePrice(videoCal.getNumberOfDays(), maxAge);

        assertThat(regularPrice).isEqualTo(150.0);
        assertThat(childrenPrice).isEqualTo(130.0);
        assertThat(NewReleasePrice).isEqualTo(1.029384522E9);

    }

}
