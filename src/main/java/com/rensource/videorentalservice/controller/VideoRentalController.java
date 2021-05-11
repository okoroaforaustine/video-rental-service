/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.controller;

import com.rensource.videorentalservice.dtos.Response;
import com.rensource.videorentalservice.dtos.VideoPriceDto;
import com.rensource.videorentalservice.dtos.VideoRentalDetailsDto;
import com.rensource.videorentalservice.entities.AppUser;
import com.rensource.videorentalservice.entities.Genre;
import com.rensource.videorentalservice.entities.Video;
import com.rensource.videorentalservice.entities.VideoPrice;
import com.rensource.videorentalservice.entities.VideoRentalDetails;
import com.rensource.videorentalservice.repositories.AppUserRepository;
import com.rensource.videorentalservice.repositories.VideoPriceRepository;
import com.rensource.videorentalservice.repositories.VideoRentalRepository;
import com.rensource.videorentalservice.repositories.VideoRepository;
import com.rensource.videorentalservice.service.VideoType;
import com.rensource.videorentalservice.service.VideoTypeFactory;
import com.rensource.videorentalservice.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author austine.okoroafor
 */
@RestController
@RequestMapping("/api/v1/VideoRental")
@Api(value = "VideoRental", description = "All API operations on VideoRental...")
@Slf4j
@CrossOrigin
@RequiredArgsConstructor
public class VideoRentalController {

    @Autowired
    VideoRentalRepository videoRentalRepo;

    @Autowired
    VideoPriceRepository priceRepo;

    @Autowired
    AppUtil appUtils;

    @Autowired
    private ModelMapper modelMapper;

    private final AppUserRepository userRepo;

    @Autowired
    VideoRepository videoRepo;

    @GetMapping(value = "/search/FilterByUsername", produces = "Application/json")
    @ApiOperation(value = "searching video rental history",
            notes = "searching video rental history",
            response = Response.class)
    public ResponseEntity<?> FilterByUsername(@RequestParam(required = false, name = "from") String fromDate,
            @RequestParam(required = false, name = "to") String toDate,
            @RequestParam(required = false, name = "username") String username,
            @RequestParam("start") int start,
            @RequestParam("limit") int limit) {

        LocalDateTime to = appUtils.toLocalDateTime(toDate);
        LocalDateTime from = appUtils.toLocalDateTime(fromDate);
        VideoRentalDetailsDto rentalDetails = new VideoRentalDetailsDto();
        List<VideoRentalDetails> list = getAllVideoListByFilter(to, from, username, start, limit, "sortBy");
        int pageSize = list.size();
        Map data = new HashMap();
        for (int i = 0; i < list.size(); i++) {

            rentalDetails.setUsername(list.get(i).getUsername());
            rentalDetails.setTitle(list.get(i).getTitle());
            rentalDetails.setVideoType(list.get(i).getVideoType());
            rentalDetails.setAmount(list.get(i).getAmount());
            rentalDetails.setCreatedDate(list.get(i).getCreatedAt());

        }

        data.put("VideoRentalsDetails", rentalDetails);

        return appUtils.returnSuccessResponse(data, start, limit, pageSize);
    }
    
     public List<VideoRentalDetails> getAllVideoListByFilter(LocalDateTime fromDate, LocalDateTime toDate, String search, Integer page, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));

        Page<VideoRentalDetails> pagedResult = videoRentalRepo.searchCriteria(search, fromDate, toDate, PageRequest.of(page, pageSize, Sort.by("created_at")));
         
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<VideoRentalDetails>();
        }
    }

    @PostMapping("/RentDetails")
    @ApiOperation(value = "Add new VideoRentalDetails",
            notes = "Add new VideoRentalDetails in the system, provided all information are correct",
            response = Response.class)
    public ResponseEntity<?> addVideoRental(@Valid @RequestBody VideoRentalDetails vidoRentDetails, @ApiIgnore Errors errors) {

        if (errors.hasErrors()) {
            return appUtils.returnPostValidationErrors(errors);
        }

        videoRentalRepo.save(vidoRentDetails);

        return appUtils.returnSuccessResponse(vidoRentDetails, "video Rented Succesfully");
    }
    
    

    @PostMapping("/CalculateVideoPrice")
    public ResponseEntity<?> CalculateVideoPrice(@Valid @RequestBody VideoPrice videotype, @ApiIgnore Errors errors) {
        VideoPriceDto priceDto = new VideoPriceDto();
        if (errors.hasErrors()) {
            return appUtils.returnPostValidationErrors(errors);
        }

        AppUser userObject = userRepo.findByUsername(videotype.getLogginUser());
        if (userObject == null) {

            return appUtils.returnErrorResponse("invalid logginUser", HttpStatus.NOT_FOUND);
        }

        Video videoObj = videoRepo.findByTitle(videotype.getTitle());
        priceDto.setLogginUser(videotype.getLogginUser());

        priceDto.setTitle(videotype.getTitle());

        priceDto.setNumberOfDays(videotype.getNumberOfDays());

        VideoTypeFactory factory = new VideoTypeFactory();
        VideoType videoType = null;

        videoType = factory.CreateVideoType("Regular");
        double regularPrice = videoType.calculatePrice(videotype.getNumberOfDays(), "");
        priceDto.setRegularRate(regularPrice);

        videoType = factory.CreateVideoType("ChildrenMovies");
        double childrenPrice = videoType.calculatePrice(videotype.getNumberOfDays(), videoObj.getMaxAge());
        priceDto.setChildrenMoviesRate(childrenPrice);

        videoType = factory.CreateVideoType("NewRelease");
        double NewReleasePrice = videoType.calculatePrice(videotype.getNumberOfDays(), "");
        priceDto.setNewRealeaseRate(NewReleasePrice);

        // convert entity to DTO
        VideoPrice saveRentalPrice = modelMapper.map(priceDto, VideoPrice.class);
        priceRepo.save(saveRentalPrice);

        return appUtils.returnSuccessResponse(priceDto, "video Price Details");

    }

   

}
