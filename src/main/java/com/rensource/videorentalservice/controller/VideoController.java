/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rensource.videorentalservice.dtos.Response;
import com.rensource.videorentalservice.dtos.VideoDto;
import com.rensource.videorentalservice.dtos.VideoPriceDto;
import com.rensource.videorentalservice.dtos.VideoRentalDetailsDto;
import com.rensource.videorentalservice.entities.AppUser;
import com.rensource.videorentalservice.entities.Genre;
import com.rensource.videorentalservice.entities.Video;
import com.rensource.videorentalservice.entities.VideoPrice;
import com.rensource.videorentalservice.entities.VideoRentalDetails;
import com.rensource.videorentalservice.repositories.GenreRepository;
import com.rensource.videorentalservice.repositories.VideoPriceRepository;
import com.rensource.videorentalservice.repositories.VideoRentalRepository;
import com.rensource.videorentalservice.repositories.VideoRepository;
import com.rensource.videorentalservice.service.VideoType;
import com.rensource.videorentalservice.service.VideoTypeFactory;
import com.rensource.videorentalservice.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/api/v1/video")
@Api(value = "Video", description = "All API operations on Video...")
@Slf4j
@CrossOrigin
@RequiredArgsConstructor
public class VideoController {

    @Autowired
    AppUtil appUtils;
    @Autowired
    VideoRepository vdeoRepo;

    @Autowired
    GenreRepository genRepo;

    @Autowired
    VideoPriceRepository priceRepo;

    @Autowired
    VideoRentalRepository videoRentalRepo;

    @PostMapping("/add")
    @ApiOperation(value = "Add new video",
            notes = "Add new video in the system, provided all information are correct",
            response = Response.class)
    public ResponseEntity<?> addVideo(@Valid @RequestBody Video video, @ApiIgnore Errors errors) {

        if (errors.hasErrors()) {
            return appUtils.returnPostValidationErrors(errors);
        }

        Video newRequest = new Video();
        newRequest.setTitle(video.getTitle());
        newRequest.setType(video.getType());
        newRequest.setMaxAge(video.getMaxAge());
        newRequest.setReleaseYear(video.getReleaseYear());
        List<Genre> genre = video.getGenre();
        Video savedRequest = null;
        savedRequest = vdeoRepo.save(newRequest);
        for (Genre genreData : genre) {

            Genre gen = new Genre();
            gen.setGenreName(genreData.getGenreName());

            if (vdeoRepo.findById(savedRequest.getId()).isPresent()) {

                gen.setVideo(savedRequest);
                genRepo.save(gen);

            }

        }

        return appUtils.returnSuccessResponse(video, "video Added Succesfully");
    }

    @GetMapping(value = "/all", produces = "Application/json")
    @ApiOperation(value = "paginate Video",
            notes = "Paginates Video..",
            response = Response.class)
    public ResponseEntity<?> findAll(@RequestParam("start") int start, @RequestParam("limit") int limit) {
        VideoDto videoDto = new VideoDto();
        List<Video> list = getAllVideo(start, limit, "title");
        int size = list.size();
        Map data = new HashMap();
        for (int i = 0; i < list.size(); i++) {

            videoDto.setTitle(list.get(i).getTitle());
            videoDto.setType(list.get(i).getType());
            videoDto.setGenre(list.get(i).getGenre());

        }
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        videoDto.setLoggedInUser(username);

        data.put("VideoDetails", videoDto);

        return appUtils.returnSuccessResponse(data, start, limit, size);

    }

    public List<Video> getAllVideo(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Video> pagedResult = vdeoRepo.VideoList(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Video>();
        }
    }

}
