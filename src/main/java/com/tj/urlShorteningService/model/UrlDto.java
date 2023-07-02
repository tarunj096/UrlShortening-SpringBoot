package com.tj.urlShorteningService.model;

public class UrlDto {

    private String Url;
    private String expiryDate;

    public UrlDto(String url, String expiryDate) {
        Url = url;
        this.expiryDate = expiryDate;
    }

    public UrlDto() {
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                "Url='" + Url + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
