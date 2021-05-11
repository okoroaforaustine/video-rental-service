package com.rensource.videorentalservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VideoRentalServiceApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
       

    public static void main(String[] args) {
        SpringApplication.run(VideoRentalServiceApplication.class, args);
    }

}
