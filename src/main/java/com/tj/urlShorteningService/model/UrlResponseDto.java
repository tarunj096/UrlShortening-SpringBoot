package com.tj.urlShorteningService.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UrlResponseDto {
    private String ogUrl;
    private String shortUrl;
    private LocalDateTime expirationDate;

    public UrlResponseDto(String ogUrl, String shortUrl, LocalDateTime expirationDate) {
        this.ogUrl = ogUrl;
        this.shortUrl = shortUrl;
        this.expirationDate = expirationDate;
    }

    public UrlResponseDto() {
    }

    public String getOgUrl() {
        return ogUrl;
    }

    public void setOgUrl(String ogUrl) {
        this.ogUrl = ogUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "UrlResponseDto{" +
                "ogUrl='" + ogUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
