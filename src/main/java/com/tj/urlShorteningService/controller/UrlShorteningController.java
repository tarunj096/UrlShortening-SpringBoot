package com.tj.urlShorteningService.controller;


import com.tj.urlShorteningService.model.Url;
import com.tj.urlShorteningService.model.UrlDto;
import com.tj.urlShorteningService.model.UrlErrorResponseDto;
import com.tj.urlShorteningService.model.UrlResponseDto;
import com.tj.urlShorteningService.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlShorteningController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
        Url urltoRet = urlService.generateLink(urlDto);
        if (urltoRet != null) {
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setOgUrl(urltoRet.getOgUrl());
            urlResponseDto.setShortUrl(urltoRet.getShortLink());
            urlResponseDto.setExpirationDate(urltoRet.getExpirationDate());
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setError("Error processing your Request! Please try again..");
        urlErrorResponseDto.setStatus("404");
        return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOgUrl(@PathVariable String shortLink, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(shortLink)) {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setStatus("400");
            urlErrorResponseDto.setError("Invalid URL!");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        Url urlToRet = urlService.getEncryptedUrl(shortLink);
        if (urlToRet == null) {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setStatus("400");
            urlErrorResponseDto.setError("Url Doesn't exist or it might've expired");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        if (urlToRet.getExpirationDate().isBefore(LocalDateTime.now())) {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setStatus("200");
            urlErrorResponseDto.setError("Url has expired. Please try generating a new one");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        response.sendRedirect(urlToRet.getOgUrl());
        return null;
    }
}
