/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author austine.okoroafor
 */
@Component
public class VideoTypeFactory {

    public VideoType CreateVideoType(String type) {

        if ("Regular".equals(type)) {
            return new Regular();
        } else if ("ChildrenMovies".equals(type)) {
            return new ChildrenMovies();
        } else if ("NewRelease".equals(type)) {
            return new NewRelease();
        }

        return null;

    }

}
