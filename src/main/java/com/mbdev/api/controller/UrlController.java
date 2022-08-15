package com.mbdev.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.mbdev.api.domain.dto.CustomUrlDto;
import com.mbdev.api.domain.dto.ShortenUrlDto;
import com.mbdev.api.service.UrlConverterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.validator.routines.UrlValidator;

@RestController
public class UrlController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);
    private final UrlConverterService urlConverterService;

    private final String[] schemes = {"http", "https"};
    private final UrlValidator urlValidator = new UrlValidator(schemes);

    public UrlController(UrlConverterService urlConverterService) {
        this.urlConverterService = urlConverterService;
    }

    @PostMapping(value = "/generateShortening", consumes = {"application/json"})
    @ResponseBody
    public String createShortening(@RequestBody @Valid final ShortenUrlDto shortenRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Received URL address that convert to shortening URL : " + shortenRequest.getOriginalUrl());

        if (urlValidator.isValid(shortenRequest.getOriginalUrl())) {
            String shortenedUrl = urlConverterService.generateShortenUrl(shortenRequest.getOriginalUrl());
            LOGGER.info("Shortening URL to: " + shortenedUrl);
            response.setStatus(HttpStatus.OK.value());
            return shortenedUrl;
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
        throw new Exception("Please enter a valid URL");
    }

    @GetMapping("/redirection")
    @ResponseBody
    public RedirectView redirectToOriginalUrl(@RequestParam("shortenUrl") String shortenURL, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        LOGGER.info("Received shortening URL that will be redirect : " + shortenURL);
        String redirectUrlString = urlConverterService.getLongUrlByShortenUrl(shortenURL.trim().replace("\n", ""));
        RedirectView redirectView = new RedirectView();

        if ( redirectUrlString != null && !redirectUrlString.isEmpty() ) {
            LOGGER.info("Original URL address : " + redirectUrlString);
            urlConverterService.incrementByURL(shortenURL.trim());
            redirectView.setUrl(redirectUrlString);
            response.setStatus(HttpStatus.OK.value());
            return redirectView;
        }
        else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            throw new Exception("Original URL address can't be found");
        }
    }

    @PostMapping(value = "/customURL", consumes = {"application/json"})
    @ResponseBody
    public String customURL(@RequestBody @Valid final CustomUrlDto customURLDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("Received original URL address {} that converted to shortening URL address {} " , customURLDto.getOriginalUrl() , customURLDto.getShortenerUrl());

        if (urlValidator.isValid(customURLDto.getOriginalUrl()) && urlValidator.isValid(customURLDto.getShortenerUrl())) {
            String shortenedUrl = urlConverterService.generateShortenUrl(customURLDto.getShortenerUrl(), customURLDto.getOriginalUrl());
            LOGGER.info("Shortening URL to: " + shortenedUrl);
            response.setStatus(HttpStatus.OK.value());
            return shortenedUrl;
        }

        response.setStatus(HttpStatus.NOT_FOUND.value());
        throw new Exception("Please enter a valid URL address");
    }
}




