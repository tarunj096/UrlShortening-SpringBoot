package com.tj.urlShorteningService.service;

import com.tj.urlShorteningService.model.Url;
import com.tj.urlShorteningService.model.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {

    public Url generateLink(UrlDto urlDto);
    public Url persistLink(Url url);
    public Url getEncryptedUrl(String url);
    public void deleteLnk(Url url);

}
